package com.wj.core.service.experience;

import com.wj.core.repository.activity.CouponCodeRepository;
import com.wj.core.repository.activity.CouponRepository;
import com.wj.core.repository.experience.ExperienceCodeRepository;
import com.wj.core.repository.experience.ExperienceRepository;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExperienceTask implements Job {

    @Autowired
    private ExperienceService experienceService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer objId = (Integer) map.get("objectId");
        experienceService.updateExperienceIsShowAndStatus("1", "3", new Date(), objId);
    }
}
