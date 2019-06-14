package com.wj.admin.controller.auth;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.user.dto.RoleAuthorityDTO;
import com.wj.core.entity.user.embeddable.RoleAuthority;
import com.wj.core.entity.user.embeddable.UserRole;
import com.wj.core.service.auth.RoleAuthorityService;
import com.wj.core.service.user.RoleService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.service.user.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/v1/roleAuthority", tags = "角色路由接口模块")
@RestController
@RequestMapping("/roleAuthority/")
public class RoleAuthorityController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleAuthorityService roleAuthorityService;


    @ApiOperation(value = "新增/修改角色路由", notes = "新增/修改角色路由")
    @PostMapping("addRoleAuthority")
    public ResponseMessage addRoleAuthority(@RequestBody RoleAuthorityDTO roleAuthorityDTO) {
        roleAuthorityService.saveRoleAuthority(roleAuthorityDTO);
        return ResponseMessage.ok();
    }


}
