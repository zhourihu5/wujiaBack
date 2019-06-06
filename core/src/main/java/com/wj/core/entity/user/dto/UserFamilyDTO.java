package com.wj.core.entity.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户家庭关联表")
public class UserFamilyDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "家庭id")
    private Integer familyId;
    @ApiModelProperty(value = "1、房产所有人 2、使用人")
    private Integer identity;
    @ApiModelProperty(value = "1、允许使用 0、禁止使用")
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
