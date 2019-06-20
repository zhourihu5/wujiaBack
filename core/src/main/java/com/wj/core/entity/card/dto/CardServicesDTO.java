package com.wj.core.entity.card.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

@Data
public class CardServicesDTO {

    private Integer id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "剪辑")
    private String memo;
    @ApiModelProperty(value = "封面")
    private String cover;
    @ApiModelProperty(value = "地址")
    private String url;
    @ApiModelProperty(value = "包名")
    private String packageName;
    @ApiModelProperty(value = "服务类型 1.发现 2.政务")
    private Integer type;
    @ApiModelProperty(value = "类别 1.本周 2.人气 等")
    private Integer category;
    @ApiModelProperty(value = "标识 1.app 2.链接")
    private Integer flag;
    @ApiModelProperty(value = "状态 1.可用 0.不可用")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "订阅数量")
    @Transient
    private Integer subscribeNum;
    @ApiModelProperty(value = "是否订阅 0.否 1.是")
    private Integer isSubscribe;

}
