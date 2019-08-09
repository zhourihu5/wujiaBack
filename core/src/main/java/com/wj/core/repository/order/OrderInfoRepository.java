package com.wj.core.repository.order;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.order.OrderInfo;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer>, JpaSpecificationExecutor<OrderInfo> {

    @Query(value = "select * from ebiz_order_info", nativeQuery = true)
    public Page<OrderInfo> findAll(Pageable pageable);

    @Query(value = "select * from ebiz_order_info where status = ?1", nativeQuery = true)
    public Page<OrderInfo> findAllByStatus(String status, Pageable pageable);

    @Query(value = "select * from ebiz_order_info where id = ?1", nativeQuery = true)
    public OrderInfo findByOrderId(Integer id);

    @Query(value = "select * from ebiz_order_info where code = ?1", nativeQuery = true)
    public OrderInfo findOrderByCode(String code);

    @Query(value = "select * from ebiz_order_info where user_id = ?1 and activity_id = ?2 and status in (2,3)", nativeQuery = true)
    public OrderInfo findByUserIdAndActivityId(Integer userId, Integer activityId);

    @Query(value = "select * from ebiz_order_info where activity_id = ?1 and status in (2,3)", nativeQuery = true)
    public List<OrderInfo> findByActivityId(Integer activityId);

    @Query(value = "select * from ebiz_order_info where activity_id = ?1 and status in (2,3)", nativeQuery = true)
    public Page<OrderInfo> findByActivityId(Integer activityId, Pageable pageable);

    @Query(value = "select * from ebiz_order_info where user_id = ?1 and status in (2,3)", nativeQuery = true)
    public List<OrderInfo> findByUserId(Integer userId);

    @Query(value = "select count(*) from ebiz_order_info where activity_id = ?1 and status in (2,3)", nativeQuery = true)
    public Integer findCountByActivityId(Integer activityId);

    @Query("update OrderInfo o set o.status = ?1 where o.id = ?2")
    @Modifying
    void modityStatus(String status, Integer id);

    @Query("update OrderInfo o set o.status = ?1, o.receiveDate = ?2 where o.id = ?3")
    @Modifying
    void saveStatusAndDate(String status, Date receiveDate, Integer id);

    @Query("update OrderInfo o set o.code = ?1 where o.id = ?2")
    @Modifying
    void modityCode(String code, Integer id);

    @Query("update OrderInfo o set o.code = ?2 where o.code = ?1")
    @Modifying
    void updateWxOrderByCode(String code, String wxOrderCode);
}
