package com.wj.admin.controller.auth;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.user.SysAuthority;
import com.wj.core.service.auth.AuthorityService;
import com.wj.core.service.auth.RoleAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/authority", tags = "权限接口模块")
@RestController
@RequestMapping("/v1/authority/")
public class AuthorityController {

    public final static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RoleAuthorityService roleAuthorityService;

    @ApiOperation(value = "获取路由列表", notes = "获取路由列表")
    @GetMapping("findAll")
    public ResponseMessage<List<SysAuthority>> findAll() {
        List<SysAuthority> list = authorityService.findAll();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "新增/修改接口", notes = "新增/修改接口")
    @PostMapping("addAuthority")
    public ResponseMessage addAuthority(@RequestBody SysAuthority sysAuthority) {
        authorityService.saveAuthority(sysAuthority);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "删除接口", notes = "删除接口")
    @PostMapping("delAuthority")
    public ResponseMessage delAuthority(@RequestBody SysAuthority sysAuthority) {
        authorityService.delAuthority(sysAuthority);
        return ResponseMessage.ok();
    }


}
