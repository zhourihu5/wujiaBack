package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.embeddable.FamilyCommuntity;
import com.wj.core.entity.user.embeddable.UserFamily;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 家庭社区关联表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "家庭社区关联表")
@Entity
@Table(name = "sys_family_communtity")
public class SysFamilyCommuntity {

    @EmbeddedId
    private FamilyCommuntity familyCommuntity;

    public FamilyCommuntity getFamilyCommuntity() {
        return familyCommuntity;
    }

    public void setFamilyCommuntity(FamilyCommuntity familyCommuntity) {
        this.familyCommuntity = familyCommuntity;
    }
}
