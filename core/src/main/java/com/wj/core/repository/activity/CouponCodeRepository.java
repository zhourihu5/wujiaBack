package com.wj.core.repository.activity;

import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.CouponCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CouponCodeRepository extends JpaRepository<CouponCode, Integer>, JpaSpecificationExecutor<CouponCode> {

    @Query(value = "select * from ebiz_coupon_code where status = ?1 and coupon_type = ?2 and user_id = ?3", nativeQuery = true)
    public Page<CouponCode> findByStatusAndType(String status, String type, Integer userId, Pageable pageable);

    @Query(value = "select * from ebiz_coupon_code where status in (1,2) and coupon_type = ?1 and user_id = ?2", nativeQuery = true)
    public Page<CouponCode> findByStatusAndType(String type, Integer userId, Pageable pageable);

    @Query(value = "select * from ebiz_coupon_code where status = 0 and activity_id = ?1 and coupon_type = ?2 and user_id = ?3", nativeQuery = true)
    public List<CouponCode> findByTypeAndUserId(Integer activityId, String type, Integer userId);

    @Query(value = "select * from ebiz_coupon_code where status = 0 and coupon_type = ?1 and user_id = ?2", nativeQuery = true)
    public List<CouponCode> findByTypeAndUserId(String type, Integer userId);

    @Query(value = "select * from ebiz_coupon_code where status = 0 and id = ?1", nativeQuery = true)
    public CouponCode findByCouponId(Integer id);

    @Query(value = "select count(*) from ebiz_coupon_code where activity_id = ?1 and coupon_type = ?2 and user_id = ?3", nativeQuery = true)
    public Integer getCountByTypeAndUserId(Integer activityId, String type, Integer userId);

    @Query(value = "select * from ebiz_coupon_code where activity_id = ?1 and coupon_type = ?2 and user_id = ?3", nativeQuery = true)
    public List<CouponCode> getByActivityIdAndTypeAndUserId(Integer activityId, String type, Integer userId);

    @Query(value = "select count(*) from ebiz_coupon_code where activity_id = ?1 and coupon_type = ?2 and user_id is not null", nativeQuery = true)
    public Integer getCountByTypeAndActivityId(Integer activityId, String type);

    @Query(value = "select * from ebiz_coupon_code where status = 0 and user_id is null and coupon_type = ?1 and activity_id = ?2", nativeQuery = true)
    public List<CouponCode> findListByTypeAndActivityId(String type, Integer activityId);

    @Modifying
    @Query("update CouponCode c set c.userId = ?1, c.userName = ?2, c.updateDate = ?3 where c.id = ?4")
    public void updateCouponCodeByCode(Integer userId, String userName, Date date, Integer id);

    @Modifying
    @Query("update CouponCode c set c.status = ?1, c.updateDate = ?2 where c.id = ?3")
    public void updateStatusById(String status, Date date, Integer id);

    @Modifying
    @Query("update CouponCode c set c.status = ?1, c.updateDate = ?2 where c.couponId = ?3 and c.status = 0")
    public void updateStatusByCouponIdAndStatus(String status, Date date, Integer couponId);


    @Modifying
    @Query("delete from CouponCode e where e.couponId = ?1")
    void deleteByCouponId(Integer id);
}
