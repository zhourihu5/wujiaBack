package com.wj.core.entity.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "消息未读实体")
public class MessageReadDTO {


    @ApiModelProperty(value = "消息主键ID")
    private Integer id;

}
