package com.wj.core.entity.op.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@ApiModel(description = "服务表")
@Data
public class OpServiceDTO {

    @ApiModelProperty(value = "服务id，分割")
    private String service;
    @ApiModelProperty(value = "服务类型 1.发现 2.政务")
    private Integer type;
}
