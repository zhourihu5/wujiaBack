package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 版本信息表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "版本信息表")
@Entity
public class SysVersion {

    private Integer id;
    @ApiModelProperty(value = "版本号")
    private String sysVer;
    @ApiModelProperty(value = "")
    private String showVer;
    @ApiModelProperty(value = "包名")
    private String pName;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysVer() {
        return sysVer;
    }

    public void setSysVer(String sysVer) {
        this.sysVer = sysVer;
    }

    public String getShowVer() {
        return showVer;
    }

    public void setShowVer(String showVer) {
        this.showVer = showVer;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
