package com.wj.core.entity.card.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "创建卡片实体类")
@Data
public class CreateCardDTO {

    private Integer id;
    private String title;
    private String memo;
    @ApiModelProperty(value = "0.功能 1.外链 2.内链 3.图文")
    private String cardType;
    private String url;
    private Date pushDate;
    private String content;
    @ApiModelProperty(value = "0 默认排序 非0 传过来就好")
    private Integer location;
    @ApiModelProperty(value = "多个服务以把ID用逗号拼接")
    private String services;
    private String path;

}
