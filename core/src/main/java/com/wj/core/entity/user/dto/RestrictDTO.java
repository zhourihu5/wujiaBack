package com.wj.core.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 限行表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "限行表")
@Data
public class RestrictDTO {
    private Integer id;
    @ApiModelProperty(value = "限行号码")
    private String num;
    @ApiModelProperty(value = "限行时间")
    private Date createDate;
}
