package com.wj.core.service.order;

import com.wj.core.entity.order.OrderInfo;
import com.wj.core.service.activity.ActivityService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderTask implements Job {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer objId = (Integer) map.get("objectId");
        OrderInfo orderInfo = orderService.getOrder(objId);
        if (orderInfo.getStatus().equals("1")) {
            orderService.closeOrder(objId);
        }
    }
}
