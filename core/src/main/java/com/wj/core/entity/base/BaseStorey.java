package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ApiModel(description = "层数表")
@Data
@Entity
public class BaseStorey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "层数名称")
    private String name;
    @ApiModelProperty(value = "层数")
    private Integer num;
    @ApiModelProperty(value = "所属单元id")
    private Integer unitId;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @ApiModelProperty(value = "节点")
    private String directory;
    @ApiModelProperty(value = "节点名称")
    private String structureName;
    @ApiModelProperty(value = "每层楼的家庭数量")
    private Integer familyCount;
    @ApiModelProperty(value = "编码")
    private String code;
    private Integer floorId;
    private Integer districtId;
    private Integer issueId;
    private String parentDirectory;
    private Integer structureId;
}
