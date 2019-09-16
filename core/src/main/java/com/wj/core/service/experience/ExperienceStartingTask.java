package com.wj.core.service.experience;

import com.wj.core.repository.experience.ExperienceRepository;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExperienceStartingTask implements Job {

    @Autowired
    private ExperienceService experienceService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer objId = (Integer) map.get("objectId");
        System.out.println(objId);
        experienceService.updateExperienceIsShowAndStatus("1", "2", new Date(), objId);
    }
}
