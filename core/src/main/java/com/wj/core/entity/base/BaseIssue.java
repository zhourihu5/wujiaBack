package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@ApiModel(description = "期")
@Data
@Entity
public class BaseIssue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "期的名称")
    private String name;
    @ApiModelProperty(value = "所属社区id")
    private Integer communtityId;
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
    private String nodeDisplay;
    @Transient
    private String communtityName;
    private String parentDirectory;
    private Integer structureId;

}
