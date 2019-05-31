package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;

/**
 * 用户家庭表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户家庭表")
@Entity
public class SysUserFamily {

    // 用户ID-外键
    @ApiModelProperty(value = "用户ID-外键")
    private Integer userId;
    // 家庭ID
    @ApiModelProperty(value = "家庭ID")
    private Integer familyId;
    // 身份 1、房产所有人 2、使用人
    @ApiModelProperty(value = "身份 1、房产所有人 2、使用人")
    private Integer identity;
    // 家庭状态
    @ApiModelProperty(value = "家庭状态")
    private Integer status;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
