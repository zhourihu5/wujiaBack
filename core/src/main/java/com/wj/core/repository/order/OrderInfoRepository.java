package com.wj.core.repository.order;

import com.wj.core.entity.order.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {
}
