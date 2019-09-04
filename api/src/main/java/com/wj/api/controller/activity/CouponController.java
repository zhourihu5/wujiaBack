package com.wj.api.controller.activity;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.CouponCode;
import com.wj.core.entity.activity.dto.CouponCodeDTO;
import com.wj.core.entity.activity.dto.CouponMessageDTO;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.service.activity.CouponCodeService;
import com.wj.core.service.activity.CouponService;
import com.wj.core.service.experience.ExperienceCodeService;
import com.wj.core.service.experience.ExperienceService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/coupon", tags = "优惠券模块")
@RestController
@RequestMapping("/v1/coupon/")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponCodeService couponCodeService;

    @ApiOperation(value = "我的优惠券列表")
    @GetMapping("couponCodeList")
    public ResponseMessage<Page<CouponCode>> couponCodeList( String type, String status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<CouponCode> page = couponCodeService.findListByStatusAndType(status, type, userId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "选择优惠券列表")
    @GetMapping("couponChangeList")
    public ResponseMessage<CouponCodeDTO> couponChangeList(Integer activityId, String type) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        CouponCodeDTO dto = couponCodeService.findChangeListByStatusAndType(activityId, type, userId);
        return ResponseMessage.ok(dto);
    }

    @ApiOperation(value = "领取活动优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "couponCode", dataType = "CouponCode", value = "领取活动优惠券")
    })
    @PostMapping("receive")
    public ResponseMessage<CouponMessageDTO> receive(@RequestBody Coupon coupon) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        String userName = (String) claims.get("userName");
        return ResponseMessage.ok(couponCodeService.receiveCouponCode(userId, userName, coupon.getId()));
    }
}
