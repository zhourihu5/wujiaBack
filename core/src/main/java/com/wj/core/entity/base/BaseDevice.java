package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
 * 设备表
 */
@ApiModel(description = "设备表")
@Data
@Entity
public class BaseDevice {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "出库时间")
    private Date outDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "安装时间")
    private Date installDate;
    @ApiModelProperty(value = "操作人")
    private String operator;
    private String versionName;
    private String versionCode;
    @Transient
    private String address;

}
