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
import com.wj.core.util.CommonUtils;
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
            // ???????????????
            List<CouponCode> yesList = new ArrayList<>();
            List<CouponCode> noList = new ArrayList<>();
            Activity activity = activityRepository.findByActivityId(activityId);
            List<CouponCode> couponCodes = couponCodeRepository.findByTypeAndUserId(type, userId);
            for (CouponCode couponCode: couponCodes) {
                Coupon coupon = couponRepository.getById(couponCode.getCouponId());
                couponCode.setCoupon(coupon);
                if (coupon.getLimitNum() == 0) {
                    if (activity.getPrice().doubleValue() >= couponCode.getMoney().doubleValue()) {
                        yesList.add(couponCode);
                    } else {
                        noList.add(couponCode);
                    }
                } else {
                    if (coupon.getLimitNum().doubleValue() <= activity.getPrice().doubleValue()) {
                        yesList.add(couponCode);
                    } else {
                        noList.add(couponCode);
                    }
                }

            }
            couponCodeDTO.setYesList(yesList);
            couponCodeDTO.setNoList(noList);
        } else {
            List<CouponCode> yesList = new ArrayList<>();
            List<CouponCode> noList = new ArrayList<>();
            // ???????????????
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
            // ???????????????
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
            // ???????????????
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


    // ?????????????????????
    @Transactional
    public CouponMessageDTO receiveCouponCode(Integer userId, String userName, Integer couponId) {
        CouponMessageDTO couponMessageDTO = new CouponMessageDTO();
        // ??????????????????????????????????????????
        Coupon coupon = couponRepository.getById(couponId);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(date);
        String endDate = formatter.format(coupon.getEndDate());
        boolean isafter = CommonUtils.isDateAfter(currentTime, endDate);
        if (isafter) {
            throw new ServiceException("?????????????????????????????????????????????!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Integer count = couponCodeRepository.getCountByTypeAndUserId(coupon.getActivityId(), coupon.getType(), userId);
        if (count >= coupon.getEveryoneNum()) {
            throw new ServiceException("?????????", ErrorCode.INTERNAL_SERVER_ERROR);
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
////            throw new ServiceException("?????????????????????????????????", ErrorCode.INTERNAL_SERVER_ERROR);
//        }
            couponMessageDTO.setCouponCode(newCouponCode);
        }
        return couponMessageDTO;
    }

}
