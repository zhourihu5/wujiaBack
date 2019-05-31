package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;

/**
 * 用户角色权限表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户角色权限表")
@Entity
public class SysRoleAuthority {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    @ApiModelProperty(value = "权限ID")
    private Integer authId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

}
