package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.CommonUtils;
import com.wj.admin.utils.HttpUtils;
import com.wj.admin.utils.JwtUtil;
import com.wj.admin.utils.ResultUtil;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysRole;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.user.RoleService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/role", tags = "用户接口模块")
@RestController
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping("findAll")
    public ResponseMessage<List<SysRole>> findAll() {
        List<SysRole> list = roleService.findAll();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "新增/修改角色", notes = "新增/修改角色")
    @PostMapping("addRole")
    public ResponseMessage addRole(@RequestBody SysRole sysRole) {
        roleService.saveRole(sysRole);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @PostMapping("delRole")
    public ResponseMessage delRole(@RequestBody SysRole sysRole) {
        roleService.delRole(sysRole);
        return ResponseMessage.ok();
    }

}
