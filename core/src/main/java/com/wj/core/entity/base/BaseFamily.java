package com.wj.core.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;

/*
 * 家庭表
 */
@ApiModel(description = "家庭表")
@Entity
public class BaseFamily {

    private Integer id;
    // 门牌号
    @ApiModelProperty(value = "门牌号")
    private Integer num;
    // 所属单元id
    @ApiModelProperty(value = "所属单元id")
    private Integer unitId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

}
