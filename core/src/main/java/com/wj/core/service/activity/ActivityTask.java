package com.wj.core.service.activity;

import com.wj.core.entity.activity.Activity;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityTask implements Job {

    @Autowired
    private ActivityService activityService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer objId = (Integer) map.get("objectId");
        activityService.modityStatusEnd(objId);
    }
}
