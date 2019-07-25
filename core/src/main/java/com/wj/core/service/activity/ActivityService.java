package com.wj.core.service.activity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.repository.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

//    public List<Activity> findList(Integer areaCode) {
//        return baseCommuntityRepository.findByAreaCode(areaCode);
//    }

}
