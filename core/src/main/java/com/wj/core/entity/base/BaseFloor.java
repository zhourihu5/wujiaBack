package com.wj.core.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * 楼号表
 */
@ApiModel(description = "楼号表")
@Entity
public class BaseFloor {
    @Id
    @GeneratedValue
    private Integer id;
    @ApiModelProperty(value = "楼的名称")
    private String name;
    @ApiModelProperty(value = "楼号")
    private Integer num;
    @ApiModelProperty(value = "共有多少个单元")
    private String unit;
    @ApiModelProperty(value = "社区id")
    private Integer communtityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(Integer communtityId) {
        this.communtityId = communtityId;
    }
}
