package com.wj.core.entity.user;

import com.wj.core.entity.user.embeddable.RoleAuthority;
import io.swagger.annotations.ApiModel;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色路由表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "角色路由表")
@Entity
@Table(name = "sys_role_authority")
public class SysRoleAuthority {

    @EmbeddedId
    private RoleAuthority roleAuthority;

    public RoleAuthority getRoleAuthority() {
        return roleAuthority;
    }

    public void setRoleAuthority(RoleAuthority roleAuthority) {
        this.roleAuthority = roleAuthority;
    }
}
