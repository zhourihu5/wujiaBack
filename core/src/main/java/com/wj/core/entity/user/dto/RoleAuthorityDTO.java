package com.wj.core.entity.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 权限表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "角色权限表")
public class RoleAuthorityDTO {
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    @ApiModelProperty(value = "权限ID")
    private Integer[] authId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer[] getAuthId() {
        return authId;
    }

    public void setAuthId(Integer[] authId) {
        this.authId = authId;
    }
}
