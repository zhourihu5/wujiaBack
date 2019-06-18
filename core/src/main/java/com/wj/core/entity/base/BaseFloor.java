package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/*
 * 楼号表
 */
@ApiModel(description = "楼号表")
@Entity
public class BaseFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "楼的名称")
    private String name;
    @ApiModelProperty(value = "楼号")
    private Integer num;
    @ApiModelProperty(value = "共有多少个单元")
    private String unit;
    @ApiModelProperty(value = "社区id")
    private Integer communtityId;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "社区名称")
    @Transient
    private String communtityName;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCommuntityName() {
        return communtityName;
    }

    public void setCommuntityName(String communtityName) {
        this.communtityName = communtityName;
    }
}
