package com.wj.core.service.order;

import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.order.OrderRepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private OrderRepairRepository orderRepairRepository;

}
