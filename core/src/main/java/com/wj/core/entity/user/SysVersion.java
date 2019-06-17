package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 版本信息表
 * @author thz
 * @version 1.0
 */
@Data
@ApiModel(description = "版本信息表")
@Entity
public class SysVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "版本号")
    private String sysVer;
    @ApiModelProperty(value = "")
    private String showVer;
    @ApiModelProperty(value = "包名")
    private String pName;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

}
