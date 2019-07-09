package com.wj.core.entity.base.dto;


import com.wj.core.entity.base.BaseArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/*
 * 省市区三级联动表
 */
@ApiModel(description = "省市区三级联动表")
public class BaseAreaDTO {

    private Integer id;
    @ApiModelProperty(value = "省市区名称")
    private String areaName;
    @ApiModelProperty(value = "省市区编号")
    private String areaCode;
    @ApiModelProperty(value = "上级主键")
    private Integer areaParentId;
    private List<BaseAreaDTO> list;

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

    public Integer getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(Integer areaParentId) {
        this.areaParentId = areaParentId;
    }

    public List<BaseAreaDTO> getList() {
        return list;
    }

    public void setList(List<BaseAreaDTO> list) {
        this.list = list;
    }
}
