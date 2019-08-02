package com.wj.core.service.activity;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.atta.AttaInfo;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.atta.AttaInfoRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    public List<Activity> findList(Integer userId, Integer communityId) {
        List<Activity> activityList = activityRepository.findByCommunityIdAndStatus(communityId, "1");
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

    public List<Activity> findList() {
        List<Activity> activityList = activityRepository.findByStatus("1");
        activityList.forEach(Activity -> {
            Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
        });
        return activityList;
    }

    public Page<Activity> findAll(Pageable pageable) {
        Page<Activity> page = activityRepository.findAll(pageable);
        page.forEach(Activity -> {
            Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
        });
        return page;
    }

    public void saveActivity(@NotNull(message = "实体未空") Activity activity) {
        if (activity.getId() == null) {
            activity.setIsShow("0"); // 未上架
            activity.setStatus("0");
        }
        if (StringUtils.isNotBlank(activity.getCover()) && StringUtils.contains(activity.getCover(),"http://")) {
            activity.setCover(activity.getCover());
        } else {
            activity.setCover(url + activity.getCover());
        }
        activityRepository.save(activity);
        // TODO 添加到定时里，让定时修改状态
    }

    // 活动更新为已经结束
    public void modityStatusEnd(Integer id) {
        activityRepository.modityStatus("3", id);
    }

    // 活动上架下架
    public void modityIsShow(Integer id, String isShow) {
        activityRepository.modityIsShow(isShow, id);
    }


    public Page<Activity> getList(Integer pageNum, Integer pageSize, Date startDate, Date endDate, String status, String title) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (startDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("startDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("endDate"), endDate));
            }
            if (StringUtils.isNotBlank(status)) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (StringUtils.isNotBlank(title)) {
                predicates.add(criteriaBuilder.equal(root.get("title"), title));
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

    public ActivityUserDTO findByActivityId(Integer activityId) {
        ActivityUserDTO activityUserDTO = new ActivityUserDTO();
        Activity activity = activityRepository.findByActivityId(activityId);
//        Date startDate = new Date();
//        Date endDate = new Date();
//        try {
//            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            startDate = sf.parse(activity.getStartDate().toString());
//            endDate = sf.parse(activity.getStartDate().toString());
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        long startTime = startDate.getTime();
//        long endTime = endDate.getTime();
//        activity.setStartTime(startTime);
//        activity.setEndTime(endTime);
        String largeMoney = activity.getSaleRules().substring(activity.getSaleRules().lastIndexOf("|") + 1);
        activity.setLargeMoney(largeMoney);
        Commodity commodity = commodityRepository.findByCommodityId(activity.getCommodityId());
        String objType = "comm";
        List<AttaInfo> attaInfoList = attaInfoRepository.findByObjectIdAndObjectType(commodity.getId(), objType);
        commodity.setAttaInfos(attaInfoList);
//        String[] strs = commodity.getFormatVal().split("|");
        String[] strs = StringUtils.split(commodity.getFormatVal(),"|");
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
        return activityUserDTO;
    }

    public List<Activity> findOtherList(Integer userId) {
        List<Activity> activityList = activityRepository.findByStatus("1");
        Scanner sc = new Scanner(System.in);
        Iterator it = activityList.iterator();
        while (it.hasNext()) {
            Activity ac = (Activity) it.next();
            // 根据用户id查询订单表活动id
            List<OrderInfo> orderInfoList = orderInfoRepository.findByUserId(userId);
            orderInfoList.forEach(OrderInfo -> {
                if (ac.getId() == OrderInfo.getActivityId()) {
                    activityList.remove(ac);
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
        activity.setCommodity(commodityRepository.findByCommodityId(activity.getCommodityId()));
        Integer count = orderInfoRepository.findCountByActivityId(activityId);
        String[] rules = activity.getSaleRules().split(",");
        Integer amount = 0;
        for (int i = 0; i < rules.length; i++) {
            Integer number = Integer.valueOf(rules[i].substring(0, rules[i].indexOf("|")));//截取|之前的字符串
            Integer money = Integer.valueOf(rules[i].substring(activity.getSaleRules().lastIndexOf("|") + 1));
            System.out.println(number + "---" + money);
            if (count < number) {
                amount = money;
                break;
            }
        }
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
        return activity;
    }
}
