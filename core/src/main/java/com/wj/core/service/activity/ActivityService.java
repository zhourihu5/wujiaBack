package com.wj.core.service.activity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
