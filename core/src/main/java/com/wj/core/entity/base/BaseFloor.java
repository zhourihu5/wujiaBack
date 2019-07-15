package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 楼号表
 */
@ApiModel(description = "楼号表")
@Data
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
    @ApiModelProperty(value = "期id")
    private Integer issueId;
    @ApiModelProperty(value = "区id")
    private Integer districtId;
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


}
