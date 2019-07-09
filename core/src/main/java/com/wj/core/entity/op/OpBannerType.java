package com.wj.core.entity.op;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 轮播图类型
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "轮播图类型")
@Data
@Entity
public class OpBannerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "模块名称")
    private String name;

}
