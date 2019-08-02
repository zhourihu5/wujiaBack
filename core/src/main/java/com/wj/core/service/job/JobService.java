package com.wj.core.service.job;

import com.google.common.collect.Sets;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.time.ClockUtil;
import com.wj.core.util.time.DateFormatUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class JobService {

    @Autowired
    private Scheduler scheduler;

    public Boolean addTask(TaskEntity info) {
        String jobName = info.getJobName(),
                jobGroup = info.getJobGroup(),
                cronExpression = info.getCronExpression(),
                jobDescription = info.getJobDescription();
        try {
            if (checkExists(jobName, jobGroup)) {
                throw new ServiceException("任务存在", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription).withSchedule(scheduleBuilder).build();
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("objectId", info.getObjectId());
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(info.getJobClass());
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).setJobData(jobDataMap).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException | ClassNotFoundException e) {
            throw new ServiceException("类名不存在或执行表达式错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Boolean resumeTask(TaskEntity taskEntity) {
        try {
            scheduler.resumeJob(JobKey.jobKey(taskEntity.getJobName(), taskEntity.getJobGroup()));
            return true;
        } catch (Exception e) {
            throw new ServiceException("开始定时任务错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    public Boolean updateTask(TaskEntity info) {
        String jobName = info.getJobName(),
                jobGroup = info.getJobGroup(),
                cronExpression = info.getCronExpression(),
                jobDescription = info.getJobDescription(),
                createTime = DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND, ClockUtil.currentDate());
        try {
            if (!checkExists(jobName, jobGroup)) {
                throw new ServiceException("任务存在", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(cronScheduleBuilder).build();

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            Set<Trigger> triggerSet = Sets.newHashSet();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
            return true;
        } catch (SchedulerException e) {
            throw new ServiceException("类名不存在或执行表达式错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Boolean pauseTask(TaskEntity taskEntity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskEntity.getJobName(), taskEntity.getJobGroup());
        try {
            if (checkExists(taskEntity.getJobName(), taskEntity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
            }
            return true;
        } catch (Exception e) {
            throw new ServiceException("停止任务错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public Boolean deleteTask(TaskEntity taskEntity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskEntity.getJobName(), taskEntity.getJobGroup());
        try {
            if (checkExists(taskEntity.getJobName(), taskEntity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
                scheduler.unscheduleJob(triggerKey); //移除触发器
                return true;
            }
        } catch (SchedulerException e) {
            throw new ServiceException("删除任务错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

    public boolean checkExists(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            return scheduler.checkExists(triggerKey);
        } catch (SchedulerException e) {
            throw new ServiceException("查询任务错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
