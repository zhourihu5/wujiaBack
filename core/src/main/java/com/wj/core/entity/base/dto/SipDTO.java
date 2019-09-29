package com.wj.core.entity.base.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@ApiModel(description = "全视通通信对象")
@Data
public class SipDTO implements Serializable {

    @ApiModelProperty(value = "sip地址")
    private String sipAddr;
    @ApiModelProperty(value = "展示名")
    private String sipDisplayname;

}
