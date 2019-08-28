package com.wj.core.service.activity;

import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.experience.Experience;
import com.wj.core.repository.activity.BlackListRepository;
import com.wj.core.repository.activity.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private BlackListRepository blackListRepository;

    @Transactional
    public void saveCoupon(Coupon coupon) {
        coupon.setCreateDate(new Date());
        coupon.setUpdateDate(new Date());
        if (coupon.getPlatform() == null) {
            coupon.setPlatform("1");
        }
        if (coupon.getActivityId() == null) {
            coupon.setActivityId(0);
        }
        if (coupon.getStatus() == null) {
            coupon.setStatus("0");
        }
        if (coupon.getGrantCount() == null) {
            coupon.setGrantCount(0);
        }
        Coupon newCoupon = couponRepository.save(coupon);
        if (coupon.getId() != null) {
            blackListRepository.deleteByCouponId(coupon.getId());
        }
        for (int i = 0; i < coupon.getUserNames().length; i++) {
            BlackList blackList = new BlackList();
            blackList.setUserName(coupon.getUserNames()[i]);
            blackList.setCouponId(newCoupon.getId());
            blackList.setCreateDate(new Date());
            blackListRepository.save(blackList);
        }
    }

    public Page<Coupon> findAllByStatus(String status, Pageable pageable) {
        Page<Coupon> page = null;
        if (status != null) {
            page = couponRepository.findByStatus(status, pageable);
        } else {
            page = couponRepository.findAllByStatus(pageable);
        }
        return page;
    }

    @Transactional
    public void updateCouponStatus(Coupon coupon) {
        couponRepository.updateCouponStatus(coupon.getStatus(), new Date(), coupon.getId());
    }

    @Transactional
    public void deleteCoupon(Integer id) {
        couponRepository.deleteById(id);
    }

}
