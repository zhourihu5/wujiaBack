package com.wj.core.entity.op.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 服务表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "服务表")
@Data
@Entity
public class OpServiceDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "服务类型 1.发现 2.政务")
    private Integer type;
}
