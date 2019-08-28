package com.wj.core.service.activity;

import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.CouponCode;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.activity.BlackListRepository;
import com.wj.core.repository.activity.CouponCodeRepository;
import com.wj.core.repository.activity.CouponRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CouponCodeRepository couponCodeRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Transactional
    public void saveCoupon(Coupon coupon) {
        if (coupon.getStatus() == null) {
            coupon.setStatus("0");
        }
        if (coupon.getStatus().equals("1")) {
            throw new ServiceException("发放状态不能编辑", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (coupon.getPlatform() == null) {
            coupon.setPlatform("1");
        }
        if (coupon.getActivityId() == null) {
            coupon.setActivityId(0);
        }
        if (coupon.getGrantCount() == null) {
            coupon.setGrantCount(0);
        }
        coupon.setCreateDate(new Date());
        coupon.setUpdateDate(new Date());
        Coupon newCoupon = couponRepository.save(coupon);
        if (coupon.getId() != null) {
            blackListRepository.deleteByCouponId(coupon.getId());
        }
        if (coupon.getUserNames() != null) {
            for (int i = 0; i < coupon.getUserNames().length; i++) {
                BlackList blackList = new BlackList();
                blackList.setUserName(coupon.getUserNames()[i]);
                blackList.setCouponId(newCoupon.getId());
                blackList.setCreateDate(new Date());
                blackListRepository.save(blackList);
            }
        }

    }

    public Page<Coupon> findAllByStatus(String status, Pageable pageable) {
        Page<Coupon> page = null;
        if (status != null) {
            page = couponRepository.findByStatus(status, pageable);
        } else {
            page = couponRepository.findAllByStatus(pageable);
        }
        page.forEach(Coupon -> {
            Coupon.setActivity(activityRepository.findByActivityId(Coupon.getActivityId()));
            List<BlackList> blackLists = blackListRepository.findAllByCouponId(Coupon.getId());
            String arrStr[] = new String[blackLists.size()];
            for (int i = 0; i < blackLists.size(); i++) {
                arrStr[i] = blackLists.get(i).getUserName();
            }
            Coupon.setUserNames(arrStr);
        });
        return page;
    }

    @Transactional
    public void updateCouponStatus(Coupon coupon) {
        if (coupon.getStatus().equals("1")) {
            List<SysUserInfo> userInfoList = userInfoRepository.findListByFlag();
            for (SysUserInfo userInfo : userInfoList) {
                CouponCode couponCode = new CouponCode();
                couponCode.setCouponId(coupon.getId());
                couponCode.setCouponType(coupon.getType());
                couponCode.setUserId(userInfo.getId());
                couponCode.setCreateDate(new Date());
                couponCodeRepository.save(couponCode);
            }
        }
        couponRepository.updateCouponStatus(coupon.getStatus(), new Date(), coupon.getId());
    }

    @Transactional
    public void deleteCoupon(Integer id) {
        couponRepository.deleteById(id);
    }

}
