package com.wj.core.entity.wx;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@ApiModel(description = "微信红包记录表")
@Entity
@Data
@Table(name = "wx_red_packet")
public class RedPacket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "微信订单号")
    private String sendListid;
    @ApiModelProperty(value = "平台订单id")
    private Integer orderId;
    @ApiModelProperty(value = "平台订单编号")
    private String orderCode;
    @ApiModelProperty(value = "订单金额")
    private Integer money;
    @ApiModelProperty(value = "用户openid")
    private String wxOpenid;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "微信平台唯一标识")
    private String wxAppid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;


}
