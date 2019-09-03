package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * 家庭表
 */
@ApiModel(description = "家庭表")
@Data
@Entity
public class BaseFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "门牌号")
    private String num;
    @ApiModelProperty(value = "所属单元id")
    private Integer unitId;
    @ApiModelProperty(value = "所属层id")
    private Integer storeyId;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @ApiModelProperty(value = "节点")
    private String directory;
    @ApiModelProperty(value = "节点名称")
    private String structureName;
    @ApiModelProperty(value = "编码")
    private String code;
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
    @ApiModelProperty(value = "门牌号")
    @Transient
    private String name;
    @ManyToMany(mappedBy = "familyId")
    @JsonIgnore
    private List<SysUserInfo> userId;
    private String parentDirectory;
    private Integer structureId;
    @Transient
    private BaseCommuntity communtity;

}
