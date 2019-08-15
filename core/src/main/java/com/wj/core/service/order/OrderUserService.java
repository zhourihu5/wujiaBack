package com.wj.core.service.order;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.order.EbizOrderUser;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.order.embeddable.OrderUser;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.order.OrderRepairRepository;
import com.wj.core.repository.order.OrderUserRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.job.JobService;
import com.wj.core.util.CommonUtils;
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
import java.util.ArrayList;
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
        EbizOrderUser ebizOrderUser = new EbizOrderUser();
        ebizOrderUser.setOrderUser(orderUser);
        orderUserRepository.save(ebizOrderUser);
    }

    @Transactional
    public void updateOrderUser(OrderUser orderUser) {
        orderUserRepository.updateOrderUser(orderUser.getUserId(), orderUser.getOrderId(), orderUser.getStatus(), new Date());
    }


    public Page<EbizOrderUser> findAll(String status, String startDate, String endDate, Pageable pageable) {
        Page<EbizOrderUser> page = null;
        if (startDate != null && endDate != null) {
            page = orderUserRepository.findAllByStatusAndDate(status, startDate, endDate, pageable);
        } else {
            page = orderUserRepository.findAllByStatus(status, pageable);
        }

        for (EbizOrderUser ebizOrderUser : page) {
            OrderInfo orderInfo = orderInfoRepository.findByOrderId(ebizOrderUser.getOrderUser().getOrderId());
            orderInfo.setCommodity(commodityRepository.findByCommodityId(orderInfo.getCommodityId()));
            orderInfo.setActivity(activityRepository.findByActivityId(orderInfo.getActivityId()));
            ebizOrderUser.setOrderInfo(orderInfo);
        }
        return page;
    }


}
