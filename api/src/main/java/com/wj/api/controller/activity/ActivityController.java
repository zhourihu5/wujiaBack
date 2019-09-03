package com.wj.api.controller.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.user.dto.XcxLoginDTO;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.upload.OssUploadService;
import com.wj.core.service.wx.WxLoginService;
import com.wj.core.service.wx.WxQrCodeService;
import com.wj.core.util.HttpClients;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/activity", tags = "活动接口模块")
@RestController
@RequestMapping("/v1/activity/")
public class ActivityController {

    public final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private WxQrCodeService wxQrCodeService;


    @ApiOperation(value = "生成小程序二维码", notes = "生成小程序二维码")
    @GetMapping("/generateQrCode")
    public ResponseMessage<String> generateQrCodeMini(Integer activityId) throws Exception {
        String path="images/wxapp/qrcode/orderConfirm";
        String fileName="activity_"+activityId+".png";

        String scene=activityId+"";
        String page="pages/orderConfirm/index";


        String result= wxQrCodeService.generateWxappQrCode(path, fileName, scene, page);
        return ResponseMessage.ok(result);

    }


    /**
     * 没参与过的活动列表
     *
     * @param
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "没参与过的活动列表", notes = "没参与过的活动列表")
    @GetMapping("findOtherList")
    public ResponseMessage findOtherList(Integer communityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        List<Activity> list = activityService.findOtherList(userId, communityId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "活动分页列表", notes = "活动分页列表")
    @GetMapping("/findAll")
    public ResponseMessage<Page<Activity>> findAll(Integer communityId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "start_date");
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        Page<Activity> page = activityService.findAll(userId, communityId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "活动全部列表", notes = "活动全部列表")
    @GetMapping("/findList")
    public ResponseMessage<List<Activity>> findList(Integer communityId) {
        List<Activity> list = activityService.findList(communityId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "活动详情", notes = "活动详情")
    @GetMapping("/findByActivityId")
    public ResponseMessage<ActivityUserDTO> findByActivityId(Integer activityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(activityService.findByActivityId(userId, activityId));
    }

    @ApiOperation(value = "确认订单", notes = "确认订单")
    @GetMapping("/isOrder")
    public ResponseMessage<Activity> isOrder(Integer activityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(activityService.isOrder(activityId, userId));
    }

    @ApiOperation(value = "小程序切换社区首页接口", notes = "小程序切换社区首页接口")
    @GetMapping("/wxIndex")
    public ResponseMessage<XcxLoginDTO> wxIndex(Integer communityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(activityService.wxIndex(communityId, userId));
    }

}
