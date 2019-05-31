package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;

/**
 * 用户角色关联表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户角色关联表")
@Entity
public class SysUserRole {

    @ApiModelProperty(value = "用户ID-外键")
    private Integer userId;
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
