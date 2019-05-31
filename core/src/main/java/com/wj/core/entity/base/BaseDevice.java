package com.wj.core.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;

/*
 * 设备表
 */
@ApiModel(description = "设备表")
@Entity
public class BaseDevice {

    private Integer id;
    @ApiModelProperty(value = "标识 1.底座 2.pad 3.门禁")
    private String flag;
    @ApiModelProperty(value = "机器唯一标识/编号")
    private String key;
    @ApiModelProperty(value = "家庭ID")
    private Integer familyId;
    @ApiModelProperty(value = "类型 1.主机 2.分机")
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
