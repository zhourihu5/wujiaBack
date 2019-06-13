package com.wj.core.entity.user.embeddable;

import io.swagger.annotations.ApiModel;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 用户角色关联表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户角色关联表")
@Embeddable
public class UserRole implements Serializable {

    private Integer userId;
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
