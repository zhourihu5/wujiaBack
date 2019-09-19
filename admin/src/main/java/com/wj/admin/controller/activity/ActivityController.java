package com.wj.admin.controller.activity;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityDTO;
import com.wj.core.entity.experience.Experience;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.util.mapper.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value = "/v1/activity", tags = "活动模块")
@RestController
@RequestMapping("/v1/activity/")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @ApiOperation(value="保存活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activity", dataType = "Activity", value = "活动对象")
    })
    @PostMapping("save")
    public ResponseMessage save(@RequestBody ActivityDTO activityDTO) {
        Activity activity = BeanMapper.map(activityDTO, Activity.class);
        activityService.saveActivity(activity);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="上架下架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isShow", dataType = "String", value = "0 下架 1上架"),
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键ID")
    })

    @GetMapping("modityIsShow")
    public ResponseMessage modityIsShow(String isShow, Integer id) {
        activityService.modityIsShow(id, isShow);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", dataType = "String", value = "结束时间"),
            @ApiImplicitParam(name = "status", dataType = "String", value = "状态 0 未开始 1 已开始 2已技术"),
            @ApiImplicitParam(name = "title", dataType = "String", value = "标题")
    })
    @GetMapping("list")
    public ResponseMessage<Page<Activity>> list(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String title) {
        return ResponseMessage.ok(activityService.getList(pageNum, pageSize, startDate, endDate, status, title));
    }


}
