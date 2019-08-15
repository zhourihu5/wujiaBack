package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@ApiModel(description = "社区黄页")
@Data
@Entity
@Table(name = "base_communtity_info")
public class BaseCommuntityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "社区id")
    private String communityId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private String province;
    private String communityCode;
    private String city;
    private String area;

}
