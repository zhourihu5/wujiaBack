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

public interface CouponCodeRepository extends JpaRepository<CouponCode, Integer>, JpaSpecificationExecutor<CouponCode> {

    @Query(value = "select * from ebiz_coupon_code where status = ?1 and coupon_type = ?2 and user_id = ?3", nativeQuery = true)
    public Page<CouponCode> findByStatusAndType(String status, String type, Integer userId, Pageable pageable);

    @Query(value = "select * from ebiz_coupon_code where status in (1,2) and coupon_type = ?1 and user_id = ?2", nativeQuery = true)
    public Page<CouponCode> findByStatusAndType(String type, Integer userId, Pageable pageable);
}
