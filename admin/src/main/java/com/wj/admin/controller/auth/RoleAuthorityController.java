package com.wj.admin.controller.auth;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.user.dto.RoleAuthorityDTO;
import com.wj.core.entity.user.embeddable.RoleAuthority;
import com.wj.core.entity.user.embeddable.UserRole;
import com.wj.core.service.auth.RoleAuthorityService;
import com.wj.core.service.user.RoleService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.service.user.UserRoleService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/v1/roleAuthority", tags = "角色路由接口模块")
@RestController
@RequestMapping("/v1/roleAuthority/")
public class RoleAuthorityController {

    private final static Logger logger = LoggerFactory.getLogger(RoleAuthorityController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleAuthorityService roleAuthorityService;


    @ApiOperation(value = "新增/修改角色路由", notes = "新增/修改角色路由")
    @PostMapping("addRoleAuthority")
    public ResponseMessage addRoleAuthority(@RequestBody RoleAuthorityDTO roleAuthorityDTO) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("新增/修改角色路由接口:/v1/roleAuthority/addRoleAuthority userId=" + claims.get("userId"));
        roleAuthorityService.saveRoleAuthority(roleAuthorityDTO);
        return ResponseMessage.ok();
    }


}
