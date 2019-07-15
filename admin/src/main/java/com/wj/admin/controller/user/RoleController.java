package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.user.SysRole;
import com.wj.core.service.user.RoleService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/role", tags = "角色接口模块")
@RestController
@RequestMapping("/v1/role/")
public class RoleController {

    private final static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping("findAll")
    public ResponseMessage<List<SysRole>> findAll() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取角色列表接口:/v1/role/findAll userId=" + claims.get("userId"));
        List<SysRole> list = roleService.findAll();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "新增/修改角色", notes = "新增/修改角色")
    @PostMapping("addRole")
    public ResponseMessage addRole(@RequestBody SysRole sysRole) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("新增/修改角色接口:/v1/role/addRole userId=" + claims.get("userId"));
        roleService.saveRole(sysRole);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @PostMapping("delRole")
    public ResponseMessage delRole(@RequestBody SysRole sysRole) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("删除角色接口:/v1/role/delRole userId=" + claims.get("userId"));
        roleService.delRole(sysRole);
        return ResponseMessage.ok();
    }

}
