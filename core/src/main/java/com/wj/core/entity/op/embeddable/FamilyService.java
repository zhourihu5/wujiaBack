package com.wj.core.entity.op.embeddable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 家庭服务订阅表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "家庭服务订阅表")
@Embeddable
public class FamilyService implements Serializable {
    private Integer serviceId;
    private Integer userId;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
