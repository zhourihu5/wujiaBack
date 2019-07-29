package com.wj.api.controller.activity;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.activity.Activity;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.wx.WxLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "/xcx/activity", tags = "用户接口模块")
@RestController
@RequestMapping("/xcx/activity/")
public class ActivityController {

    public final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    /**
     * 没参与过的活动列表
     *
     * @param
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "没参与过的活动列表", notes = "没参与过的活动列表")
    @GetMapping("findOtherList")
    public ResponseMessage findOtherList() {
        List<Activity> list = activityService.findOtherList();
        return ResponseMessage.ok(list);
    }


}
