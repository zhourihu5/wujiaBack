package com.wj.api.controller.activity;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.service.activity.ActivityService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "/v1/activity", tags = "活动接口模块")
@RestController
@RequestMapping("/v1/activity/")
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
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        List<Activity> list = activityService.findOtherList(userId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "活动分页列表", notes = "活动分页列表")
    @GetMapping("/findAll")
    public ResponseMessage<Page<Activity>> findAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "start_date");
        Page<Activity> page = activityService.findAll(pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "活动全部列表", notes = "活动全部列表")
    @GetMapping("/findList")
    public ResponseMessage<List<Activity>> findList() {
        List<Activity> list = activityService.findList();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "活动详情", notes = "活动详情")
    @GetMapping("/findByActivityId")
    public ResponseMessage<ActivityUserDTO> findByActivityId(Integer activityId) {
        return ResponseMessage.ok(activityService.findByActivityId(activityId));
    }

    @ApiOperation(value = "确认订单", notes = "确认订单")
    @GetMapping("/isOrder")
    public ResponseMessage<Activity> isOrder(Integer activityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(activityService.isOrder(activityId, userId));
    }


}
