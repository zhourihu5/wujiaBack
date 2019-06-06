package com.wj.core.entity.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/*
 * 省市区三级联动表
 */
@ApiModel(description = "省市区三级联动表")
@Entity
public class BaseArea {

    @Id
    @GeneratedValue
    private Integer id;
    @ApiModelProperty(value = "省市区名称")
    private String areaName;
    @ApiModelProperty(value = "省市区编号")
    private String areaCode;
    @ApiModelProperty(value = "区编号简称")
    private String areaShort;
    @ApiModelProperty(value = "是否热门 0.否 1.是")
    private String areaIsHot;
    @ApiModelProperty(value = "区域序列")
    private Integer areaSequence;
    @ApiModelProperty(value = "上级主键")
    private Integer areaParentId;
    @ApiModelProperty(value = "初始时间")
    private Date createDate;
    @ApiModelProperty(value = "初始地址")
    private String initAddr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaShort() {
        return areaShort;
    }

    public void setAreaShort(String areaShort) {
        this.areaShort = areaShort;
    }

    public String getAreaIsHot() {
        return areaIsHot;
    }

    public void setAreaIsHot(String areaIsHot) {
        this.areaIsHot = areaIsHot;
    }

    public Integer getAreaSequence() {
        return areaSequence;
    }

    public void setAreaSequence(Integer areaSequence) {
        this.areaSequence = areaSequence;
    }

    public Integer getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(Integer areaParentId) {
        this.areaParentId = areaParentId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getInitAddr() {
        return initAddr;
    }

    public void setInitAddr(String initAddr) {
        this.initAddr = initAddr;
    }

}
