package com.wj.api.controller.address;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.address.AddressService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/address", tags = "地址接口模块")
@RestController
@RequestMapping("/v1/address/")
public class AddressController {

    public final static Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "查询用户所在社区", notes = "查询用户所在社区")
    @GetMapping("/findCommuntity")
    public ResponseMessage<List<BaseCommuntity>> findCommuntity() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        List<BaseCommuntity> list = addressService.findByUserId(userId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "我的收获地址列表", notes = "我的收获地址列表")
    @GetMapping("/findList")
    public ResponseMessage<List<Address>> findList() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        List<Address> list = addressService.findListByUserId(userId);
        return ResponseMessage.ok(list);
    }


    @ApiOperation(value = "地址详细信息", notes = "地址详细信息")
    @GetMapping("/findDetail")
    public ResponseMessage<Address> findDetail(Integer addressId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        Address address = addressService.findByAddressId(addressId);
        return ResponseMessage.ok(address);
    }

    @ApiOperation(value = "保存地址", notes = "保存地址")
    @PostMapping("saveOrder")
    public ResponseMessage saveOrder(@RequestBody Address address) {
        addressService.saveAddress(address);
        return ResponseMessage.ok();
    }
}
