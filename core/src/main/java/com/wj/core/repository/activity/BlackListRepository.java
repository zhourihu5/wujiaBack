package com.wj.core.repository.activity;

import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.experience.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BlackListRepository extends JpaRepository<BlackList, Integer>, JpaSpecificationExecutor<BlackList> {

    @Query(value = "select * from ebiz_black_list where coupon_id = ?1", nativeQuery = true)
    public Page<BlackList> findAllByCouponId(Integer couponId, Pageable pageable);

    @Query(value = "select * from ebiz_black_list where coupon_id = ?1", nativeQuery = true)
    public List<BlackList> findAllByCouponId(Integer couponId);

    @Modifying
    @Query("delete from BlackList b where b.couponId = ?1")
    public void deleteByCouponId(Integer couponId);
}
