package com.wj.core.entity.base.embeddable;

import io.swagger.annotations.ApiModel;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 家庭社区关联表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "家庭社区关联表")
@Embeddable
public class FamilyCommuntity implements Serializable {
    private Integer familyId;
    private Integer communtityId;

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(Integer communtityId) {
        this.communtityId = communtityId;
    }
}
