package com.wj.core.service.order;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.order.OrderRepairRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.activity.ActivityTask;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.job.JobService;
import com.wj.core.service.wx.PayUserService;
import com.wj.core.util.CommonUtils;
import com.wj.core.util.number.RandomUtil;
import com.wj.core.util.time.ClockUtil;
import com.wj.core.util.time.DateFormatUtil;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private JobService jobService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PayUserService payUserService;

    // 生成订单
    @Transactional
    public OrderInfo saveOrder(OrderInfo orderInfo) {
        // 判断活动是否结束 结束不能下单
        Activity activity = activityRepository.findByActivityId(orderInfo.getActivityId());
        SysUserInfo userInfo = userInfoRepository.findByUserId(orderInfo.getUserId());
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(date);
        String endDate = formatter.format(activity.getEndDate());
//        boolean isbefore = isDateBefore(currentTime,endDate);
        boolean isafter = isDateAfter(currentTime, endDate);
        if (isafter) {
            throw new ServiceException("活动已经结束，您不能下单!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Integer count = orderInfoRepository.findCountByActivityId(activity.getCommodityId());
        String[] rules = activity.getSaleRules().split(",");
        Integer amount = 0;
        for (int i = 1; i < rules.length; i++) {
            Integer number0 = Integer.valueOf(rules[i - 1].substring(0, rules[i - 1].indexOf("|")));
            Integer number = Integer.valueOf(rules[i].substring(0, rules[i].indexOf("|")));//截取|之前的字符串
            Integer money0 = Integer.valueOf(rules[i - 1].substring(rules[i - 1].lastIndexOf("|") + 1));
            Integer money = Integer.valueOf(rules[i].substring(rules[i].lastIndexOf("|") + 1));
            System.out.println(number0 + "---" + money0);
            System.out.println(number + "---" + money);
            if (count >= number0 && count < number) {
                amount = money0;
                break;
            }
        }
        // 优惠金额
        BigDecimal favPrice;
        // 实际支付金额
        BigDecimal payMoney;
        if (activity.getSaleType().equals("1")) {
            favPrice = new BigDecimal(amount);
            // 实际支付金额
            payMoney = activity.getPrice().subtract(favPrice);
            if (payMoney.doubleValue() <= 0) {
                throw new ServiceException("系统异常", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        } else {
            BigDecimal zhe = new BigDecimal(1 - amount / 100);
            favPrice = activity.getPrice().multiply(zhe);
            payMoney = activity.getPrice().subtract(favPrice);
            if (payMoney.doubleValue() <= 0) {
                throw new ServiceException("系统异常", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
        orderInfo.setPrice(activity.getPrice());
        orderInfo.setRealPrice(payMoney);
        orderInfo.setFavPrice(favPrice);
        orderInfo.setStatus("1");
        orderInfo.setPayType("4");
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setActivityName(activity.getTitle());
        orderInfo.setCommodityCode(activity.getCommodityCode());
        orderInfo.setCreateDate(ClockUtil.currentDate());
        orderInfo.setUpdateDate(ClockUtil.currentDate());
        orderInfo.setPayEndDate(DateUtils.addMinutes(orderInfo.getCreateDate(), 15));
        String code = DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFALT_DATE, ClockUtil.currentDate()) + CommonUtils.getRandomStringByLength(24);
        orderInfo.setCode(code);
        orderInfoRepository.save(orderInfo);
        boolean ex = jobService.checkExists("order_close_" + orderInfo.getId(), "order");
        // 添加定时任务，定时关闭为支付的订单
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName("order_close_" + orderInfo.getId());
        taskEntity.setJobGroup("order");
        taskEntity.setJobClass(new OrderTask().getClass().getName());
        taskEntity.setObjectId(orderInfo.getId());
        taskEntity.setCronExpression(DateFormatUtil.formatDate(DateFormatUtil.CRON_DATE_FORMAT, DateUtils.addMinutes(orderInfo.getCreateDate(), 15)));
        if (!ex) {
            jobService.addTask(taskEntity);
        } else {
            jobService.updateTask(taskEntity);
        }
        return orderInfo;
    }

    // 订单支付时间过去
    @Transactional
    public void closeOrder(Integer id) {
        orderInfoRepository.modityStatus("4", id);
    }

    public Page<OrderInfo> findList(String status, Pageable pageable) {
        Page<OrderInfo> page = null;
        if (status == null) {
            page = orderInfoRepository.findAll(pageable);
        } else {
            if (status.equals("2,5")) {
                page = orderInfoRepository.findAllByStatus(pageable);
            } else {
                page = orderInfoRepository.findAllByStatus(status, pageable);
            }
        }
        for (OrderInfo orderInfo : page) {
            orderInfo.setCommodity(commodityRepository.findByCommodityId(orderInfo.getCommodityId()));
            orderInfo.setActivity(activityRepository.findByActivityId(orderInfo.getActivityId()));
        }
        return page;
    }

    public Page<OrderInfo> findListBD(String status, Pageable pageable) {
        Page<OrderInfo> page = null;
        if (status == null) {
            page = orderInfoRepository.findAll(pageable);
        } else {
            page = orderInfoRepository.findAllByStatusBD(status, pageable);
        }
        for (OrderInfo orderInfo : page) {
            orderInfo.setCommodity(commodityRepository.findByCommodityId(orderInfo.getCommodityId()));
            orderInfo.setActivity(activityRepository.findByActivityId(orderInfo.getActivityId()));
        }
        return page;
    }

    public OrderInfo findOrderDetail(Integer orderId) {
        OrderInfo orderInfo = orderInfoRepository.findByOrderId(orderId);
        orderInfo.setCommodity(commodityRepository.findByCommodityId(orderInfo.getCommodityId()));
        orderInfo.setActivity(activityRepository.findByActivityId(orderInfo.getActivityId()));
        return orderInfo;
    }

    public OrderInfo getOrder(Integer orderId) {
        return orderInfoRepository.findByOrderId(orderId);
    }

    public OrderInfo findOrderByCode(String code) {
        return orderInfoRepository.findOrderByCode(code);
    }

    public Page<OrderInfo> getList(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String activityName) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(startDate)) {
                try {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, startDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(endDate)) {
                try {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, endDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(status)) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (StringUtils.isNotBlank(activityName)) {
                predicates.add(criteriaBuilder.equal(root.get("activityName"), activityName));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "createDate");
        Page<OrderInfo> pageOrder = orderInfoRepository.findAll(specification, page);
        pageOrder.forEach(order -> {
            order.setCommodity(commodityRepository.findByCommodityId(order.getCommodityId()));
        });
        return pageOrder;
    }

    // 支付订单
    @Transactional
    public void payOrder(OrderInfo orderInfo) {
        OrderInfo orderInfo1 = orderInfoRepository.findByOrderId(orderInfo.getId());
        // 调用微信支付如果成功 更改订单支付状态 待收货
        orderInfoRepository.modityStatus("2", orderInfo.getId());
        commodityRepository.moditySaleNum(1, orderInfo1.getCommodityId());
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName("order_close_" + orderInfo1.getId());
        taskEntity.setJobGroup("order");
        jobService.deleteTask(taskEntity);
    }

    public void refundUserList(HttpServletRequest request, Integer activityId) {
        List<OrderInfo> orderInfoList = orderInfoRepository.findByActivityId(activityId);
        List<SysUserInfo> userInfoList = new ArrayList<>();
        orderInfoList.forEach(OrderInfo -> {
            SysUserInfo userInfo = userInfoRepository.findByUserId(OrderInfo.getUserId());
            payUserService.wxPay(request, userInfo, OrderInfo);
        });
    }


    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isDateAfter(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).after(df.parse(date2));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public void cancelOrder(Integer id) {
        orderInfoRepository.modityStatus("4", id);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName("order_close_" + id);
        taskEntity.setJobGroup("order");
        jobService.deleteTask(taskEntity);
    }

    @Transactional
    public void receiveOrder(Integer id) {
        orderInfoRepository.saveStatusAndDate("3", new Date(), id);
    }

    @Transactional
    public void deliveryOrder(Integer id) {
        orderInfoRepository.saveStatusAndDeliveryDate("3", new Date(), id);
    }

    @Transactional
    public void updateWxOrderByCode(String code, String wxOrderCode) {
        orderInfoRepository.updateWxOrderByCode(code, wxOrderCode, new Date());
    }


    @Transactional
    public Integer updateOrderDelivery(String nickName, String userName, Integer orderId) {
        return orderInfoRepository.updateOrderDelivery(nickName, userName, orderId);
    }

    @Transactional
    public Integer updateOrdeReceiveDate(Integer orderId) {
        return orderInfoRepository.updateOrdeReceiveDate(new Date(), orderId);
    }
}
