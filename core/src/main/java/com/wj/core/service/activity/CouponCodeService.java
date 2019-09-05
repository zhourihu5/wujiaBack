package com.wj.core.service.activity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.CouponCode;
import com.wj.core.entity.activity.dto.CouponCodeDTO;
import com.wj.core.entity.activity.dto.CouponMessageDTO;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.activity.BlackListRepository;
import com.wj.core.repository.activity.CouponCodeRepository;
import com.wj.core.repository.activity.CouponRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
            CouponCode.setCoupon(couponRepository.getById(CouponCode.getCouponId()));
            CouponCode.setActivity(activityRepository.findByActivityId(CouponCode.getActivityId()));
        });
        return page;
    }


    public CouponCodeDTO findChangeListByStatusAndType(Integer activityId, String type, Integer userId) {
        CouponCodeDTO couponCodeDTO = new CouponCodeDTO();
        if (type.equals("1")) {
            // 平台优惠券
            List<CouponCode> yesList = new ArrayList<>();
            List<CouponCode> noList = new ArrayList<>();
            Activity activity = activityRepository.findByActivityId(activityId);
            List<CouponCode> couponCodes = couponCodeRepository.findByTypeAndUserId(type, userId);
            for (CouponCode couponCode: couponCodes) {
                couponCode.setCoupon(couponRepository.getById(couponCode.getCouponId()));
                if (activity.getPrice().doubleValue() >= couponCode.getMoney().doubleValue()) {
                    yesList.add(couponCode);
                } else {
                    noList.add(couponCode);
                }
            }
            couponCodeDTO.setYesList(yesList);
            couponCodeDTO.setNoList(noList);
        } else {
            List<CouponCode> yesList = new ArrayList<>();
            List<CouponCode> noList = new ArrayList<>();
            // 活动优惠券
            List<CouponCode> couponCodes = couponCodeRepository.findByTypeAndUserId(type, userId);
            for (CouponCode couponCode: couponCodes) {
                couponCode.setCoupon(couponRepository.getById(couponCode.getCouponId()));
                couponCode.setActivity(activityRepository.findByActivityId(couponCode.getActivityId()));
                if (couponCode.getActivityId().equals(activityId)) {
                    yesList.add(couponCode);
                } else {
                    noList.add(couponCode);
                }
            }
            couponCodeDTO.setYesList(yesList);
            couponCodeDTO.setNoList(noList);
        }
        return couponCodeDTO;
    }


    public Integer yesCount(Integer activityId, String type, Integer userId) {
        Integer count = 0;
        if (type.equals("1")) {
            // 平台优惠券
            List<CouponCode> yesList = new ArrayList<>();
            Activity activity = activityRepository.findByActivityId(activityId);
            List<CouponCode> couponCodes = couponCodeRepository.findByTypeAndUserId(type, userId);
            for (CouponCode couponCode: couponCodes) {
                couponCode.setCoupon(couponRepository.getById(couponCode.getCouponId()));
                if (activity.getPrice().doubleValue() >= couponCode.getMoney().doubleValue()) {
                    yesList.add(couponCode);
                }
            }
            count = yesList.size();
        } else {
            List<CouponCode> yesList = new ArrayList<>();
            // 活动优惠券
            List<CouponCode> couponCodes = couponCodeRepository.findByTypeAndUserId(type, userId);
            for (CouponCode couponCode: couponCodes) {
                couponCode.setCoupon(couponRepository.getById(couponCode.getCouponId()));
                couponCode.setActivity(activityRepository.findByActivityId(couponCode.getActivityId()));
                if (couponCode.getActivityId().equals(activityId)) {
                    yesList.add(couponCode);
                }
            }
            count = yesList.size();
        }
        return count;
    }


    // 领取活动优惠券
    @Transactional
    public CouponMessageDTO receiveCouponCode(Integer userId, String userName, Integer couponId) {
        CouponMessageDTO couponMessageDTO = new CouponMessageDTO();
        // 先验证每个人领取多少张的限制
        Coupon coupon = couponRepository.getById(couponId);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(date);
        String endDate = formatter.format(coupon.getEndDate());
        boolean isafter = isDateAfter(currentTime, endDate);
        if (isafter) {
            throw new ServiceException("活动优惠券已经结束，您不能领取!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Integer count = couponCodeRepository.getCountByTypeAndUserId(coupon.getActivityId(), coupon.getType(), userId);
        if (count >= coupon.getEveryoneNum()) {
            throw new ServiceException("已领取", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Integer allCount = couponCodeRepository.getCountByTypeAndActivityId(coupon.getActivityId(), coupon.getType());
        if (allCount >= coupon.getGrantCount()) {
            couponMessageDTO.setFlag(false);
        } else {
            CouponCode couponCode = new CouponCode();
            couponCode.setCouponId(couponId);
            couponCode.setCouponType(coupon.getType());
            couponCode.setActivityId(coupon.getActivityId());
            couponCode.setMoney(coupon.getMoney());
            couponCode.setUserId(userId);
            couponCode.setUserName(userName);
            couponCode.setStatus("0");
            couponCode.setCreateDate(new Date());
            couponCode.setUpdateDate(new Date());
            couponCode.setFinishDate(coupon.getEndDate());
            CouponCode newCouponCode = couponCodeRepository.save(couponCode);
//        List<CouponCode> couponCodes = couponCodeRepository.findListByTypeAndActivityId(coupon.getType(), coupon.getActivityId());
//        if (couponCodes.size() > 0) {
//            couponCodeRepository.updateCouponCodeByCode(userId, userName, new Date(), couponCodes.get(0).getId());
//            couponMessageDTO.setFlag(false);
//        } else {
//            couponMessageDTO.setFlag(false);
////            throw new ServiceException("很遗憾，已经被抢购一空", ErrorCode.INTERNAL_SERVER_ERROR);
//        }
            couponMessageDTO.setCouponCode(newCouponCode);
        }
        return couponMessageDTO;
    }

    public static boolean isDateAfter(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).after(df.parse(date2));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
