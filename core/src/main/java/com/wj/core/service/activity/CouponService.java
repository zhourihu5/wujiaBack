package com.wj.core.service.activity;

import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.CouponCode;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.activity.BlackListRepository;
import com.wj.core.repository.activity.CouponCodeRepository;
import com.wj.core.repository.activity.CouponRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.job.JobService;
import com.wj.core.service.order.OrderTask;
import com.wj.core.util.time.DateFormatUtil;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CouponService {
    @Value("${wj.oss.access}")
    private String url;
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

    @Autowired
    private JobService jobService;

    @Transactional
    public void saveCoupon(Coupon coupon) {
        if (coupon.getActivityId() == null) {
            coupon.setActivityId(0);
        }
        if (coupon.getId() == null && coupon.getActivityId() != 0) {
            Coupon coupon1 = couponRepository.getByActivityId(coupon.getActivityId());
            if (coupon1 != null) {
                throw new ServiceException("此活动已经存在优惠券", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
        if (coupon.getStatus() == null) {
            coupon.setStatus("0");
        }
        if (coupon.getStatus().equals("1")) {
            throw new ServiceException("发放状态不能编辑", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (coupon.getPlatform() == null) {
            coupon.setPlatform("1");
        }
        if (coupon.getGrantCount() == null) {
            coupon.setGrantCount(0);
        }
        if (StringUtils.isNotBlank(coupon.getCover()) && StringUtils.contains(coupon.getCover(),"https://")) {
            coupon.setCover(coupon.getCover());
        } else {
            coupon.setCover(url + coupon.getCover());
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
        boolean ex = jobService.checkExists("coupon_close_" + coupon.getId(), "coupon");
        // 添加定时任务
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName("coupon_close_" + coupon.getId());
        taskEntity.setJobGroup("coupon");
        taskEntity.setJobClass(new CouponTask().getClass().getName());
        taskEntity.setObjectId(coupon.getId());
        taskEntity.setCronExpression(DateFormatUtil.formatDate(DateFormatUtil.CRON_DATE_FORMAT, coupon.getEndDate()));
        if (!ex) {
            jobService.addTask(taskEntity);
        } else {
            jobService.updateTask(taskEntity);
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

    public Page<Coupon> findListByType(String type, Pageable pageable) {
        Page<Coupon> page = couponRepository.findByType(type, pageable);
        return page;
    }

    @Transactional
    public void updateCouponStatus(Coupon coupon) {
        Coupon coupon1 = couponRepository.getById(coupon.getId());
        if (coupon.getStatus().equals("1")) {
            List<SysUserInfo> userInfoList = userInfoRepository.findListByFlag();
            for (SysUserInfo userInfo : userInfoList) {
                CouponCode couponCode = new CouponCode();
                couponCode.setCouponId(coupon.getId());
                couponCode.setCouponType(coupon.getType());
                couponCode.setActivityId(coupon.getActivityId());
                couponCode.setUserId(userInfo.getId());
                couponCode.setUserName(userInfo.getUserName());
                couponCode.setMoney(coupon1.getMoney());
                couponCode.setStatus("0");
                couponCode.setCreateDate(new Date());
                couponCode.setUpdateDate(new Date());
                couponCode.setFinishDate(coupon1.getEndDate());
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
