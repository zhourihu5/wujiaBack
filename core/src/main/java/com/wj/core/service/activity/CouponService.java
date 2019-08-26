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
        Coupon newCoupon = couponRepository.save(coupon);
        if (coupon.getStatus().equals("1")) {
            for (int i = 0; i < coupon.getUserNames().length; i++) {
                BlackList blackList = new BlackList();
                blackList.setUserName(coupon.getUserNames()[i]);
                blackList.setCouponId(newCoupon.getId());
                blackList.setCreateDate(new Date());
                blackListRepository.save(blackList);
            }
        }
//        else if (coupon.getStatus().equals("2")) {
//
//        }
    }

    public Page<Coupon> findAllByStatus(String status, Pageable pageable) {
        Page<Coupon> page = null;
        if (status != null) {
            page = couponRepository.findAllByStatus(status, pageable);
        } else {
            page = couponRepository.findAll(pageable);
        }
        return page;
    }

}
