package com.wj.core.service.activity;

import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.CouponCode;
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
import java.util.Date;
import java.util.List;

@Service
public class CouponCodeService {

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


    public Page<CouponCode> findListByStatusAndType(String status, String type, Integer userId, Pageable pageable) {
        Page<CouponCode> page = null;
        if (status.equals("1,2")) {
            page = couponCodeRepository.findByStatusAndType(type, userId, pageable);
        } else {
            page = couponCodeRepository.findByStatusAndType(status, type, userId, pageable);
        }
        page.forEach(CouponCode -> {
            CouponCode.setActivity(activityRepository.findByActivityId(CouponCode.getActivityId()));
        });
        return page;
    }

   
}
