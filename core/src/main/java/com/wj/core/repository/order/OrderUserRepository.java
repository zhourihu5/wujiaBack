package com.wj.core.repository.order;

import com.wj.core.entity.order.OrderUser;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface OrderUserRepository extends JpaRepository<OrderUser, Integer> {


    @Query(value = "select * from ebiz_order_user where user_id = ?1", nativeQuery = true)
    public List<OrderUser> findListByUserId(Integer userId);

    @Query(value = "select * from ebiz_order_user where user_id = ?1", nativeQuery = true)
    public Page<OrderUser> findPageByUserId(Integer userId, Pageable pageable);

    @Query(value = "select * from ebiz_order_user where user_id = ?1 and status = ?2", nativeQuery = true)
    public Page<OrderUser> findAllByStatus(Integer userId, String status, Pageable pageable);

    @Modifying
    @Query(value = "update ebiz_order_user set status = ?3 , finish_date = ?4 where user_id = ?1 and order_id = ?2", nativeQuery = true)
    public void updateOrderUser(Integer userId, Integer orderId, String status, Date date);

    @Query(value = "select * from ebiz_order_user where user_id = ?1 and status = ?2 and finish_date >= ?3 and finish_date <= ?4 ", nativeQuery = true)
    public Page<OrderUser> findAllByStatusAndDate(Integer userId, String status, String startDate, String endDate, Pageable pageable);

    @Query(value = "select count(*) from ebiz_order_user where order_id = ?1", nativeQuery = true)
    public Integer findCountByOrderId(Integer orderId);

    @Query(value = "select count(*) from ebiz_order_user where user_id = ?1 and status = ?2", nativeQuery = true)
    public Integer findAllCountByStatus(Integer userId, String status);

}
