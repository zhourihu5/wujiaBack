package com.wj.core.service.activity;

import com.wj.core.repository.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

}
