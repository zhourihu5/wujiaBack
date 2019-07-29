package com.wj.core.entity.order;

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
    private String deliveryArea;
    private String status;
    private String remark;
    private Date createDate;
    private Date payDate;
    private String closeRemark;
    private String expressCode;
    private String expressName;
    private Integer shopId;
    private String shopCode;
    private String shopName;
    private String platForm;
    private Date updateDate;
    private Integer commodityId;
    @Transient
    private Commodity commodity;
}