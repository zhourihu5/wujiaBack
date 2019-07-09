package com.wj.core.entity.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/*
 * 设备表
 */
@ApiModel(description = "设备表")
@Entity
public class BaseDeviceDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "标识 1.底座 2.pad 3.门禁")
    private Integer flag;
    @ApiModelProperty(value = "机器唯一标识/编号")
    private String deviceKey;
    @ApiModelProperty(value = "家庭ID")
    private Integer familyId;
    @ApiModelProperty(value = "类型 1.主机 2.分机")
    private Integer type;
    @ApiModelProperty(value = "pad标号(01 02 03 04...)")
    private String buttonKey;
    @ApiModelProperty(value = "0.未安装 1.已安装 2.淘汰")
    private Integer status;
    @ApiModelProperty(value = "出库时间")
    private Date outDate;
    @ApiModelProperty(value = "安装时间")
    private Date installDate;
    @ApiModelProperty(value = "操作人")
    private String operator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
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

    public String getButtonKey() {
        return buttonKey;
    }

    public void setButtonKey(String buttonKey) {
        this.buttonKey = buttonKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
