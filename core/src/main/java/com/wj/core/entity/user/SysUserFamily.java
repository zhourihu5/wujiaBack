package com.wj.core.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.embeddable.UserFamily;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户家庭关联表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户家庭关联表")
@Entity
@Table(name = "sys_user_family")
public class SysUserFamily {

    @EmbeddedId
    private UserFamily userFamily;
    @ApiModelProperty(value = "1、房产所有人 2、使用人")
    private Integer identity;
//    @ApiModelProperty(value = "0、禁止使用 1、允许使用 ")
//    private Integer status;

    public UserFamily getUserFamily() {
        return userFamily;
    }

    public void setUserFamily(UserFamily userFamily) {
        this.userFamily = userFamily;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }


}
