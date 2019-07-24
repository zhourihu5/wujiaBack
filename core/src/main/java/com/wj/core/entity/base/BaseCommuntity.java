package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*
 * 社区表
 */
@ApiModel(description = "社区表")
@Data
@Entity
@Table(name = "base_communtity")
public class BaseCommuntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "省编号")
    private Integer province;
    @ApiModelProperty(value = "市编号")
    private Integer city;
    @ApiModelProperty(value = "区编号")
    private Integer area;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @Transient
    private String areaName;
    @Transient
    private String cityName;
    @Transient
    private String provinceName;
    @Transient
    private String NodeDisplay;
    private String flag;


}
