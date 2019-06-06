package com.wj.core.entity.user.embeddable;

import io.swagger.annotations.ApiModel;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 用户家庭关联表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户家庭关联表")
@Embeddable
public class UserFamily implements Serializable {
    private Integer userId;
    private Integer familyId;

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
}
