package com.wj.core.service.activity;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CommodityRepository commodityRepository;

    public List<Activity> findList() {
        List<Activity> activityList = activityRepository.findByStatus("1");
        activityList.forEach(Activity -> {
            Activity.setCommodity(commodityRepository.findByCommodityId(Activity.getCommodityId()));
        });
        return activityList;
    }

    public void saveActivity(@NotNull(message = "实体未空") Activity activity) {
        if (activity.getId() == null)
            activity.setIsShow("0"); // 未上架
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
        return pageCard;
    }

}
