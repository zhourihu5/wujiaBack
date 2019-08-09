package com.wj.core.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.commodity.Commodity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ebiz_order_info")
@Data
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String nickName;
    private String code;
    private BigDecimal price;
    private BigDecimal realPrice;
    private BigDecimal favPrice;
    private BigDecimal freight;
    private String payType;
    private String deliveryUname;
    private String deliveryUphone;
    private String deliveryAddress;
    private String deliveryArea;
//    订单状态 1.待付款 2.待收货 3.已收货 4.已过期
    private String status;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private Date payDate;
    private String closeRemark;
    private String expressCode;
    private String expressName;
    private Integer shopId;
    private String shopCode;
    private String shopName;
    private Date updateDate;
    private Integer commodityId;
    private String commodityCode;
    private Integer activityId;
    private String activityCode;
    private String activityName;
    private String deliveryPerson;
    private String deliveryPhone;
    private Integer deliveryHour;
    private String wxOrderCode;
    //确认收货时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveDate;
    //送货送达时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deliveryDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payEndDate;
    @Transient
    private Commodity commodity;
    @Transient
    private Activity activity;
}
