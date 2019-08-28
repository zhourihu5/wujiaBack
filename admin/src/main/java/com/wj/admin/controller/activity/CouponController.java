package com.wj.admin.controller.activity;

import com.wj.admin.controller.base.BaseFamilyController;
import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.dto.CouponDTO;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.service.activity.CouponService;
import com.wj.core.service.experience.ExperienceService;
import com.wj.core.util.mapper.BeanMapper;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/coupon", tags = "优惠券模块")
@RestController
@RequestMapping("/v1/coupon/")
public class CouponController {

    private final static Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponService couponService;

    @ApiOperation(value = "保存优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupon", dataType = "Coupon", value = "优惠券对象")
    })
    @PostMapping("save")
    public ResponseMessage save(@RequestBody CouponDTO couponDTO) {
        Coupon coupon = BeanMapper.map(couponDTO, Coupon.class);
        couponService.saveCoupon(coupon);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "删除优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键ID")
    })
    @PostMapping("remove")
    public ResponseMessage remove(@RequestBody Coupon coupon) {
        couponService.deleteCoupon(coupon.getId());
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "优惠券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "status", dataType = "String", value = "状态")
    })
    @GetMapping("list")
    public ResponseMessage<Page<Coupon>> list(String status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("优惠券列表 接口:/v1/coupon/list userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<Coupon> page = couponService.findAllByStatus(status, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "发放优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupon", dataType = "Coupon", value = "优惠券对象")
    })
    @PostMapping("updateStatus")
    public ResponseMessage updateStatus(@RequestBody Coupon coupon) {
        couponService.updateCouponStatus(coupon);
        return ResponseMessage.ok();
    }


}
