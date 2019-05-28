package com.api.entity;

/**
 * 用户家庭表
 * @author thz
 * @version 1.0
 */
public class UserFamily {

    // 用户id-外键
    private int userId;
    // 家庭id
    private String familyId;
    // 身份 1、房产所有人 2、使用人
    private String identity;
    // 家庭状态
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
