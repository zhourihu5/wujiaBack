package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/*
 * 社区表
 */
@ApiModel(description = "社区的围墙门表")
@Data
@Entity
@Table(name = "base_community_lock")
public class BaseCommuntityLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "节点")
    private String directory;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "社区id")
    private Integer communityId;
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
//    @ApiModelProperty(value = "开锁距离")
//    private Integer distance;


}
