package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
 * 单元表
 */
@ApiModel(description = "单元表")
@Data
@Entity
public class BaseUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "单元号")
    private String num;
    @ApiModelProperty(value = "每个单元共有多少楼层")
    private Integer storey;
    @ApiModelProperty(value = "楼ID")
    private Integer floorId;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @ApiModelProperty(value = "节点")
    private String directory;
    @ApiModelProperty(value = "节点名称")
    private String structureName;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "社区名称")
    @Transient
    private String communtityName;
    @ApiModelProperty(value = "楼名称")
    @Transient
    private String floorName;

}
