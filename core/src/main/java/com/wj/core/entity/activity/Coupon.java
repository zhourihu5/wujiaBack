package com.wj.core.entity.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.commodity.Commodity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ebiz_coupon")
@Data
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "优惠券名称")
    private String name;
    @ApiModelProperty(value = "优惠卷类型  1平台优惠券 2活动优惠券")
    private String type;
    @ApiModelProperty(value = "面值")
    private BigDecimal money;
    @ApiModelProperty(value = "使用平台 1 所有、2小程序、3pad")
    private String platform;
    @ApiModelProperty(value = "每人限制数量（只有平台优惠券有）")
    private Integer everyoneNum;
    @ApiModelProperty(value = "限制条件(0.无限制 其余的是限制金额)")
    private Integer limitNum;
    @ApiModelProperty(value = "0代表全场通用， 其他存活动ID")
    private Integer activityId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "发放总数 （活动优惠卷才有）")
    private Integer grantCount;
    @ApiModelProperty(value = "领取方式  1主动领取 2自动发放")
    private String receive;
    @ApiModelProperty(value = "封面")
    private String cover;
    @ApiModelProperty(value = "状态")
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    @Transient
    private String[] userNames;
}
