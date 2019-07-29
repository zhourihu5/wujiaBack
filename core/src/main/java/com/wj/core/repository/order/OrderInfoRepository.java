package com.wj.core.repository.order;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.order.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {

    @Query(value = "select * from ebiz_order_info where status = ?1", nativeQuery = true)
    public Page<OrderInfo> findAllByStatus(String status, Pageable pageable);

    @Query(value = "select * from ebiz_order_info where id = ?1", nativeQuery = true)
    public OrderInfo findByOrderId(Integer id);
}
