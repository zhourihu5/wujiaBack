package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.*;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.util.CommonUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.checkerframework.checker.units.qual.min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/user", tags = "用户接口模块")
@RestController
@RequestMapping("/v1/user/")
public class UserInfoController {

    private final static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(value = "获取用户分页信息", notes = "获取用户分页信息")
    @GetMapping("findUserInfoByName")
    public Object findUserInfoByName(String userName, String nickName, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取用户分页信息接口:/v1/user/findUserInfoByName userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<SysUserInfo> page = userInfoService.findAll(userName, nickName, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "新增/修改用户", notes = "新增/修改用户")
    @PostMapping("addUser")
    public Object addUser(@RequestBody SysUserInfo user) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("新增/修改用户接口:/v1/user/addUser userId=" + claims.get("userId"));
        AesUtils au = new AesUtils();
        user.setPassword(au.AESEncode(CommonUtils.AESKEY, user.getPassword()));
        userInfoService.saveUser(user);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @PostMapping("delUser")
    public Object delUser(@RequestBody SysUserInfo user) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("删除用户接口:/v1/user/delUser userId=" + claims.get("userId"));
        userInfoService.delUser(user);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取商家用户列表")
    @GetMapping("shopUser")
    public ResponseMessage<Page<SysUserInfo>> delUser(Integer pageNum, Integer pageSize) {
        return ResponseMessage.ok(userInfoService.getShopUserList(pageNum, pageSize));
    }

    @ApiOperation(value = "根据用户ID获取用户信息")
    @GetMapping("getUserInfo")
    public ResponseMessage<SysUserInfo> delUser(Integer userId) {
        return ResponseMessage.ok(userInfoService.findUserInfo(userId));
    }

}
