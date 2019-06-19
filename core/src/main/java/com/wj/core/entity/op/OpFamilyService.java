package com.wj.core.entity.op;

import com.wj.core.entity.op.embeddable.FamilyService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 家庭服务订阅表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "家庭服务订阅表")
@Entity
public class OpFamilyService {

    @ApiModelProperty(value = "是否订阅 0.否 1.是")
    private Integer isSubscribe;
    @EmbeddedId
    private FamilyService familyService;

    public Integer getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public FamilyService getFamilyService() {
        return familyService;
    }

    public void setFamilyService(FamilyService familyService) {
        this.familyService = familyService;
    }
}
