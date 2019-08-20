package com.wj.admin.controller.refund;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.wx.PayUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "/v1/refund", tags = "退款模块")
@RestController
@RequestMapping("/v1/refund/")
public class RefundController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayUserService payUserService;

    @GetMapping("refundList")
    public ResponseMessage refundList(HttpServletRequest request, Integer activityId) {
        // 退款列表 根据活动id出所有参与人员
        orderService.refundUserList(request, activityId);
        return ResponseMessage.ok();
    }


}
