package com.wj.core.service.order;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.address.Address;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.order.OrderRepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Activity findOrder(Integer activityId, Integer userId) {
        Activity activity = activityRepository.findByActivityId(activityId);
        Address address = addressRepository.findByAddressId(userId, "1");
        activity.setAddress(address);
        return activity;
    }

}
