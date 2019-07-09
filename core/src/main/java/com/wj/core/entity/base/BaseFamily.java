package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * 家庭表
 */
@ApiModel(description = "家庭表")
@Entity
public class BaseFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "门牌号")
    private String num;
    @ApiModelProperty(value = "所属单元id")
    private Integer unitId;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @Transient
    private String communtityName;
    @ApiModelProperty(value = "楼名称")
    @Transient
    private String floorName;
    @ApiModelProperty(value = "单元名称")
    @Transient
    private String unitName;
    @Transient
    private Integer communtityId;
    @Transient
    private SysUserInfo userInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public SysUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SysUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(Integer communtityId) {
        this.communtityId = communtityId;
    }

    @ManyToMany(mappedBy = "familyId")
    @JsonIgnore
    private List<SysUserInfo> userId;

    public List<SysUserInfo> getUserId() {
        return userId;
    }

    public void setUserId(List<SysUserInfo> userId) {
        this.userId = userId;
    }



}
