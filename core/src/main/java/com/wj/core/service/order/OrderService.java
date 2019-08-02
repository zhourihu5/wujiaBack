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

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
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
    public void saveOrder(OrderInfo orderInfo) {
        orderInfo.setStatus("1");
        orderInfo.setCreateDate(new Date());
        orderInfo.setUpdateDate(new Date());
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
        }
        return page;
    }

    public OrderInfo findOrderDetail(Integer orderId) {
        OrderInfo orderInfo = orderInfoRepository.findByOrderId(orderId);
        orderInfo.setCommodity(commodityRepository.findByCommodityId(orderInfo.getCommodityId()));
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
}
