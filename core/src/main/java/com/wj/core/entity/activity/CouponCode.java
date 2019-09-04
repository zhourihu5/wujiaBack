package com.wj.core.entity.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ebiz_coupon_code")
@Data
public class CouponCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "用户手机号")
    private String userName;
    @ApiModelProperty(value = "优惠券ID")
    private Integer couponId;
    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal money;
    @ApiModelProperty(value = "活动ID")
    private Integer activityId;
    @ApiModelProperty(value = "优惠卷类型  1平台优惠券 2活动优惠券")
    private String couponType;
    @ApiModelProperty(value = "状态 0.未使用 1.已使用 2.已过期")
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;
    @Transient
    private Activity activity;
    @Transient
    private Coupon coupon;
}
