package com.wj.core.service.order;

import com.wj.core.entity.order.OrderUser;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.order.OrderUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderUserService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private OrderUserRepository orderUserRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CommodityRepository commodityRepository;

    @Transactional
    public void saveOrderUser(OrderUser orderUser) {
        orderInfoRepository.modityStatus("5", orderUser.getOrderId());
//        OrderUser ebizOrderUser = new OrderUser();
//        ebizOrderUser.setOrderUser(orderUser);
        orderUserRepository.save(orderUser);
    }

    @Transactional
    public void updateOrderUser(OrderUser orderUser) {
        orderUserRepository.updateOrderUser(orderUser.getUserId(), orderUser.getOrderId(), orderUser.getStatus(), new Date());
    }


    public Page<OrderUser> findAll(Integer userId, String status, String startDate, String endDate, Pageable pageable) {
        Page<OrderUser> page = null;
        if (startDate != null && endDate != null) {
            page = orderUserRepository.findAllByStatusAndDate(userId, status, startDate, endDate, pageable);
        } else {
            page = orderUserRepository.findAllByStatus(userId, status, pageable);
        }

        for (OrderUser orderUser : page) {
            OrderInfo orderInfo = orderInfoRepository.findByOrderId(orderUser.getOrderId());
            orderInfo.setCommodity(commodityRepository.findByCommodityId(orderInfo.getCommodityId()));
            orderInfo.setActivity(activityRepository.findByActivityId(orderInfo.getActivityId()));
            orderUser.setOrderInfo(orderInfo);
        }
        return page;
    }


    public Integer findCountByOrderId(Integer orderId) {
        return orderUserRepository.findCountByOrderId(orderId);
    }

}
