package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.user.embeddable.UserRole;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.service.user.UserRoleService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/userRole", tags = "用户角色接口模块")
@RestController
@RequestMapping("/v1/userRole/")
public class UserRoleController {

    private final static Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRoleService userRoleService;


    @ApiOperation(value = "新增/修改用户角色", notes = "新增/修改用户角色")
    @PostMapping("addUserRole")
    public ResponseMessage addUserRole(@RequestBody UserRole userRole) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("新增/修改用户角色接口:/v1/userRole/addUserRole userId=" + claims.get("userId"));
        userRoleService.saveUserRole(userRole);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "删除用户角色", notes = "删除用户角色")
    @PostMapping("delUserRole")
    public ResponseMessage delUserRole(@RequestBody UserRole userRole) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("删除用户角色接口:/v1/userRole/delUserRole userId=" + claims.get("userId"));
        userRoleService.delUserRole(userRole);
        return ResponseMessage.ok();
    }

}
