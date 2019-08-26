package com.wj.core.repository.activity;

import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer>, JpaSpecificationExecutor<Coupon> {

    @Query(value = "select * from ediz_coupon where status= ?1", nativeQuery = true)
    public Page<Coupon> findAllByStatus(String status, Pageable pageable);

    @Modifying
    @Query("update Coupon c set c.status = ?1, c.updateDate = ?2 where c.id = ?3")
    public void updateCouponStatus(String status, Date date, Integer id);
}
