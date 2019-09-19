package com.wj.core.service.activity;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.CouponCode;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.atta.AttaInfo;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.XcxLoginDTO;
import com.wj.core.helper.impl.RedisHelperImpl;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.activity.CouponCodeRepository;
import com.wj.core.repository.activity.CouponRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.atta.AttaInfoRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.address.AddressService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.job.JobService;
import com.wj.core.service.message.MessageService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.util.time.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    @Value("${wj.oss.access}")
    private String url;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AttaInfoRepository attaInfoRepository;
    @Autowired
    private JobService jobService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserFamilyService userFamilyService;
    @Autowired
    private BaseFamilyService baseFamilyService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CouponCodeService couponCodeService;
    @Autowired
    private CouponCodeRepository couponCodeRepository;
    @Autowired
    private RedisHelperImpl redisHelper;

    public List<Activity> findList(Integer userId, Integer communityId) {
        List<Activity> activityList = activityRepository.findByCommunityIdAndIsShow(communityId, "1");
        activityList.forEach(Activity -> {
            Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
            if (userId != null) {
                OrderInfo orderInfo = orderInfoRepository.findByUserIdAndActivityId(userId, Activity.getId());
                if (orderInfo != null) {
                    Activity.setIsJoin(1);
                } else {
                    Activity.setIsJoin(0);
                }
            }
        });
        return activityList;
    }

    public List<Activity> getTop3Activity() {
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Activity> page = activityRepository.findByIsShow("1", pageable);
        page.forEach(Activity -> {
            Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
        });
        return page.getContent();
    }

    public List<Activity> findList(Integer communityId) {
        List<Activity> activityList = activityRepository.findByIsShow("1", communityId);
        activityList.forEach(Activity -> {
            Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
        });
        return activityList;
    }

    public Page<Activity> findAll(Integer userId, Integer communityId, Pageable pageable) {
        if (communityId != null) {
            Page<Activity> page = activityRepository.findAll("1", communityId, pageable);
            page.forEach(Activity -> {
                Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
                if (userId != null) {
                    OrderInfo orderInfo = orderInfoRepository.findByUserIdAndActivityId(userId, Activity.getId());
                    if (orderInfo != null) {
                        Activity.setIsJoin(1);
                    } else {
                        Activity.setIsJoin(0);
                    }
                }
            });
            return page;
        } else {
            return activityRepository.findByIsShow("1", pageable);
        }
    }

    @Transactional
    public void saveActivity(Activity activity) {

        if (activity.getId() == null) {
            activity.setIsShow("0"); // 未上架
            activity.setStatus("0");
        }

        if (StringUtils.isNotBlank(activity.getCover()) && StringUtils.contains(activity.getCover(), "https://")) {
            activity.setCover(activity.getCover());
        } else {
            activity.setCover(url + activity.getCover());
        }
        if (StringUtils.isNotBlank(activity.getGiftImg()) && StringUtils.contains(activity.getGiftImg(), "https://")) {
            activity.setGiftImg(activity.getGiftImg());
        } else {
            activity.setGiftImg(url + activity.getGiftImg());
        }
        activity.setCreateDate(new Date());
        activityRepository.save(activity);
        boolean ex = jobService.checkExists("activity_update_status_" + activity.getId(), "activity");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName("activity_update_status_" + activity.getId());
        taskEntity.setJobGroup("activity");
        taskEntity.setJobClass(new ActivityTask().getClass().getName());
        taskEntity.setObjectId(activity.getId());
        taskEntity.setCronExpression(DateFormatUtil.formatDate(DateFormatUtil.CRON_DATE_FORMAT, activity.getEndDate()));
        if (!ex) {
            jobService.addTask(taskEntity);
        } else {
            jobService.updateTask(taskEntity);
        }
    }

    // 活动更新为已经结束
    @Transactional
    public void modityStatusEnd(Integer id) {
        activityRepository.modityStatus("3", id);
        activityRepository.modityIsShow("0", id);

    }

    // 活动上架下架
    @Transactional
    public void modityIsShow(Integer id, String isShow) {
        activityRepository.modityIsShow(isShow, id);
    }


    public Page<Activity> getList(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String title) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(startDate)) {
                try {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, startDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(endDate)) {
                try {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, endDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(status)) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (StringUtils.isNotBlank(title)) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Activity> pageCard = activityRepository.findAll(specification, page);
        pageCard.forEach(activity -> {
            activity.setCommodity(commodityRepository.findByCommodityId(activity.getCommodityId()));
        });
        return pageCard;
    }

    public ActivityUserDTO findByActivityId(Integer userId, Integer activityId) {
        ActivityUserDTO activityUserDTO = new ActivityUserDTO();
        Activity activity = activityRepository.findByActivityId(activityId);
        if (activity.getIsShow().equals("0")) {
            throw new ServiceException("活动已经下架", ErrorCode.INTERNAL_SERVER_ERROR);
        }
//        String largeMoney = activity.getSaleRules().substring(activity.getSaleRules().lastIndexOf("|") + 1);
//        activity.setLargeMoney(largeMoney);
        Commodity commodity = commodityRepository.findByCommodityId(activity.getCommodityId());
        String objType = "comm";
        List<AttaInfo> attaInfoList = attaInfoRepository.findByObjectIdAndObjectType(commodity.getId(), objType);
        commodity.setAttaInfos(attaInfoList);
        String[] strs = StringUtils.split(commodity.getFormatVal(), "|");
        String[] rules = StringUtils.split(activity.getSaleRules(), ",");
//        if (activity.getSaleType().equals("1")) {
        // 钱
//            for (String rule : rules) {
//                String[] r = StringUtils.split(rule, "|");
//                int salePersonNum = Integer.valueOf(r[0]);
//                if (commodity.getSalesNum() < salePersonNum) {
//                    activity.setSaleTip(salePersonNum - commodity.getSalesNum() + "," + r[1]);
//                    break;
//                }
//            }
        // TODO 后台去掉折暂时不判断
//        }
        commodity.setFormatVals(strs);
        activity.setCommodity(commodity);
        Integer pageNum = 0;
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "create_date");
        Page<OrderInfo> orderInfoPage = orderInfoRepository.findByActivityId(activityId, pageable);
        List<SysUserInfo> userInfoList = new ArrayList<>();
        orderInfoPage.forEach(OrderInfo -> {
            SysUserInfo userInfo = userInfoRepository.findByUserId(OrderInfo.getUserId());
            userInfoList.add(userInfo);
        });
        activityUserDTO.setActivity(activity);
        activityUserDTO.setUserInfoList(userInfoList);
        Coupon coupon = couponRepository.getByActivityId(activityId);
        if (coupon != null) {
            coupon.setUserCouponCount(0);
//            Integer count = couponCodeRepository.getCountByTypeAndUserId(coupon.getActivityId(), coupon.getType(), userId);

            List<CouponCode> statusList = couponCodeRepository.getByActivityIdAndTypeAndUserId(coupon.getActivityId(), coupon.getType(), userId);
            Integer count = statusList.size();
            if (count >= coupon.getLimitNum()) {
                coupon.setUserCouponCount(count);
                coupon.setValid(false);
                for (CouponCode couponCode : statusList) {
                    if ("0".equals(couponCode.getStatus())) {
                        coupon.setValid(true);
                        break;
                    }
                }
            }
            activityUserDTO.setCoupon(coupon);
        }
        return activityUserDTO;
    }


    public List<Activity> findOtherList(Integer userId, Integer communityId) {
        List<Activity> activityList = activityRepository.findByIsShow("1", communityId);
//        Scanner sc = new Scanner(System.in);
        Iterator it = activityList.iterator();
        while (it.hasNext()) {
            Activity ac = (Activity) it.next();
            // 根据用户id查询订单表活动id
            List<OrderInfo> orderInfoList = orderInfoRepository.findByUserId(userId);
            orderInfoList.forEach(OrderInfo -> {
                if (ac.getId() == OrderInfo.getActivityId()) {
                    it.remove();
                }
            });
        }
        activityList.forEach(Activity -> {
            Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
        });
        return activityList;
    }

    public Activity isOrder(Integer activityId, Integer userId) {
        Activity activity = activityRepository.findByActivityId(activityId);
//        if (activity.getIsShow().equals("0")) {
//            throw new ServiceException("活动已经下架", ErrorCode.INTERNAL_SERVER_ERROR);
//        }
        activity.setCommodity(commodityRepository.findByCommodityId(activity.getCommodityId()));
        Integer count = orderInfoRepository.findCountByActivityId(activityId);
        Integer amount = 0;
//        String[] rules = activity.getSaleRules().split(",");
//        for (int i = 1; i < rules.length; i++) {
//            Integer number0 = Integer.valueOf(rules[i-1].substring(0, rules[i-1].indexOf("|")));
//            Integer number = Integer.valueOf(rules[i].substring(0, rules[i].indexOf("|")));//截取|之前的字符串
//            Integer money0 = Integer.valueOf(rules[i-1].substring(rules[i-1].lastIndexOf("|") + 1));
//            Integer money = Integer.valueOf(rules[i].substring(rules[i].lastIndexOf("|") + 1));
//            System.out.println(number0 + "---" + money0);
//            System.out.println(number + "---" + money);
//            if (count >= number0 && count < number ) {
//                amount = money0;
//                break;
//            }
//        }
        BigDecimal num = new BigDecimal(amount);
        BigDecimal payMoney = activity.getPrice().subtract(num);
        if (payMoney.doubleValue() <= 0) {
            throw new ServiceException("系统异常", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        activity.setPaymentMoney(payMoney);
        List<Address> addressList = addressRepository.findByUserId(userId);
        if (addressList.size() > 0) {
            activity.setAddress(addressList.get(0));
        }
        activity.setPlatformCouponCount(couponCodeService.yesCount(activityId, "1", userId));
        activity.setActivityCouponCount(couponCodeService.yesCount(activityId, "2", userId));
        return activity;
    }

    public XcxLoginDTO wxIndex(Integer communityId, Integer userId) {
        XcxLoginDTO loginDTO = new XcxLoginDTO();
        List<BaseCommuntity> communtityList = addressService.findByUserId(userId);
        loginDTO.setCommuntityList(communtityList);
        Commodity commodity = commodityRepository.findByCommodityId(communityId);
        List<Activity> activityList;
        if (commodity != null) {
            loginDTO.setCommuntityName(commodity.getName());
        }
        if (communityId != null) {
            activityList = activityService.findList(userId, communityId);
        } else {
            // modity sun 用户未登录请看下返回前三条
            activityList = activityService.getTop3Activity();
        }
        loginDTO.setActivityList(activityList);
        loginDTO.setUnRead(messageService.isUnReadMessage(userId, 0));
        List<BaseFamily> familyList = new ArrayList<>();
        List<SysUserFamily> userFamilyList = userFamilyService.findByUserId(userId);
        for (SysUserFamily sysUserFamily : userFamilyList) {
            BaseFamily baseFamily = new BaseFamily();
            baseFamily.setId(sysUserFamily.getUserFamily().getFamilyId());
            BaseCommuntity baseCommuntity = baseFamilyService.findCommuntityByFamilyId1(sysUserFamily.getUserFamily().getFamilyId());
            baseFamily.setName(baseCommuntity.getAllName());
            baseFamily.setCommuntity(baseCommuntity);
            familyList.add(baseFamily);
        }
        loginDTO.setFamilyList(familyList);
        return loginDTO;
    }
}
