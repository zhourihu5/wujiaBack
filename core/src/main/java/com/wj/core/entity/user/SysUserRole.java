package com.wj.core.entity.user;

import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.entity.user.embeddable.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 用户角色表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户角色表")
@Entity
@Table(name = "sys_user_role")
public class SysUserRole {

    @EmbeddedId
    private UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
