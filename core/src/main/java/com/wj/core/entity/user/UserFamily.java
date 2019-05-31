package com.wj.core.entity.user;

import javax.persistence.Entity;

/**
 * 用户家庭表
 * @author thz
 * @version 1.0
 */
@Entity
public class UserFamily {

    // 用户id-外键
    private Integer userId;
    // 家庭id
    private Integer familyId;
    // 身份 1、房产所有人 2、使用人
    private Integer identity;
    // 家庭状态
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
