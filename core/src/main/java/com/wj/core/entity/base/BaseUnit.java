package com.wj.core.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * 单元表
 */
@ApiModel(description = "单元表")
@Entity
public class BaseUnit {
    @Id
    @GeneratedValue
    private Integer id;
    @ApiModelProperty(value = "单元号")
    private Integer num;
    @ApiModelProperty(value = "每个单元共有多少楼层")
    private Integer storey;
    @ApiModelProperty(value = "楼ID")
    private Integer floorId;

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

    public Integer getStorey() {
        return storey;
    }

    public void setStorey(Integer storey) {
        this.storey = storey;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

}
