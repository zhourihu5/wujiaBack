package com.wj.core.repository.order;

import com.wj.core.entity.order.EbizOrderUser;
import com.wj.core.entity.order.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderUserRepository extends JpaRepository<EbizOrderUser, Integer> {


    @Query(value = "select * from ebiz_order_user where user_id = ?1", nativeQuery = true)
    public List<EbizOrderUser> findListByUserId(Integer userId);

    @Query(value = "select * from ebiz_order_user where user_id = ?1", nativeQuery = true)
    public Page<EbizOrderUser> findPageByUserId(Integer userId, Pageable pageable);

    @Query(value = "select * from ebiz_order_user where status = ?1", nativeQuery = true)
    public Page<EbizOrderUser> findAllByStatus(String status, Pageable pageable);
}
