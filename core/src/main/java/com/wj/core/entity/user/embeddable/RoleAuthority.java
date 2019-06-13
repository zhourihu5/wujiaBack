package com.wj.core.entity.user.embeddable;

import io.swagger.annotations.ApiModel;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 角色路由关联表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "角色路由关联表")
@Embeddable
public class RoleAuthority implements Serializable {

    private Integer roleId;
    private Integer authorityId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }
}
