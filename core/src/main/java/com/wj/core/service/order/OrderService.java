package com.wj.core.service.order;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.order.OrderRepairRepository;
import com.wj.core.service.activity.ActivityTask;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.job.JobService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private OrderRepairRepository orderRepairRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private JobService jobService;

    // 生成订单
    public OrderInfo saveOrder(OrderInfo orderInfo) {
        // 判断活动是否结束 结束不能下单
        Activity activity = activityRepository.findByActivityId(orderInfo.getActivityId());
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(date);
        String endDate = formatter.format(activity.getEndDate());
//        boolean isbefore = isDateBefore(currentTime,endDate);
        boolean isafter = isDateAfter(currentTime,endDate);
        if (isafter) {
            throw new ServiceException("活动已经结束，您不能下单!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Integer count = orderInfoRepository.findCountByActivityId(activity.getCommodityId());
        String[] rules = activity.getSaleRules().split(",");
        Integer amount = 0;
        for (int i = 1; i < rules.length; i++) {
            Integer number0 = Integer.valueOf(rules[i-1].substring(0, rules[i-1].indexOf("|")));
            Integer number = Integer.valueOf(rules[i].substring(0, rules[i].indexOf("|")));//截取|之前的字符串
            Integer money0 = Integer.valueOf(rules[i-1].substring(rules[i-1].lastIndexOf("|") + 1));
            Integer money = Integer.valueOf(rules[i].substring(rules[i].lastIndexOf("|") + 1));
            System.out.println(number0 + "---" + money0);
            System.out.println(number + "---" + money);
            if (count >= number0 && count < number ) {
                amount = money0;
                break;
            }
        }
        // 优惠金额
        BigDecimal favPrice = new BigDecimal(amount);
        // 实际支付金额
        BigDecimal payMoney = activity.getPrice().subtract(favPrice);
        if (payMoney.doubleValue() <= 0) {
            throw new ServiceException("系统异常", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        orderInfo.setPrice(activity.getPrice());
        orderInfo.setRealPrice(payMoney);
        orderInfo.setFavPrice(favPrice);
        orderInfo.setStatus("1");
        orderInfo.setCreateDate(ClockUtil.currentDate());
        orderInfo.setUpdateDate(ClockUtil.currentDate());
        orderInfo.setPayDate(DateUtils.addMinutes(orderInfo.getCreateDate(), 15));
        orderInfoRepository.save(orderInfo);
        String code = DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFALT_DATE, ClockUtil.currentDate()) + StringUtils.left(orderInfo.getId().toString(), 8);
        orderInfoRepository.modityCode(code, orderInfo.getId());
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
    public void closeOrder(Integer id) {
        orderInfoRepository.modityStatus("4", id);
    }

    public Page<OrderInfo> findList(String status, Pageable pageable) {
        Page<OrderInfo> page = null;
        if (status == null) {
            page = orderInfoRepository.findAll(pageable);
        } else {
            page = orderInfoRepository.findAllByStatus(status, pageable);
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

    public Page<OrderInfo> getList(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String activityName) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(startDate)) {
                try {
                    predicates.add(criteriaBuilder.equal(root.get("createDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, startDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(endDate)) {
                try {
                    predicates.add(criteriaBuilder.equal(root.get("createDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, endDate)));
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
        // 先判断订单状态是否是待付款 是待付款查询订单实付金额调用微信支付
        OrderInfo orderInfo1 = orderInfoRepository.findByOrderId(orderInfo.getId());
        if (!orderInfo1.getStatus().equals("1")) {
            throw new ServiceException("此订单不能支付", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        // 调用微信支付如果成功 更改订单支付状态 待收货
        orderInfoRepository.modityStatus("2", orderInfo.getId());
        // 调用微信支付失败 告诉前端支付失败
    }




    public static boolean isDateBefore(String date1,String date2){
        try{
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        }catch(ParseException e){
            System.out.println( e.getMessage());
            return false;
        }
    }

    public static boolean isDateAfter(String date1,String date2){
        try{
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).after(df.parse(date2));
        }catch(ParseException e){
            System.out.println( e.getMessage());
            return false;
        }
    }

    @Transactional
    public void cancelOrder(Integer id) {
        orderInfoRepository.modityStatus("4", id);
    }

    @Transactional
    public void receiveOrder(Integer id) {
        orderInfoRepository.modityStatus("3", id);
    }
}
