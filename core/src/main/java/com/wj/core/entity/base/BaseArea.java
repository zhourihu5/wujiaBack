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
    private String areaSequence;
    @ApiModelProperty(value = "上级主键")
    private String areaParentId;
    @ApiModelProperty(value = "初始时间")
    private Date initDate;
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

    public String getAreaSequence() {
        return areaSequence;
    }

    public void setAreaSequence(String areaSequence) {
        this.areaSequence = areaSequence;
    }

    public String getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(String areaParentId) {
        this.areaParentId = areaParentId;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public String getInitAddr() {
        return initAddr;
    }

    public void setInitAddr(String initAddr) {
        this.initAddr = initAddr;
    }

}
