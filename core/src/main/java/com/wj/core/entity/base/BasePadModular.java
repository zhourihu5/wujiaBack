package com.wj.core.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;

/*
 * 设备模块表
 */
@ApiModel(description = "设备模块表")
@Entity
public class BasePadModular {

    private Integer id;
    @ApiModelProperty(value = "模块名称")
    private String name;
    @ApiModelProperty(value = "菜单级别")
    private Integer lev;
    @ApiModelProperty(value = "父ID")
    private Integer parentId;
    @ApiModelProperty(value = "标识_简称")
    private String flag;
    @ApiModelProperty(value = "类型 1.内联 2.功能")
    private Integer type;
    @ApiModelProperty(value = "头像")
    private String icon;

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

    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
