package com.wj.core.entity.order;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ebiz_order_repair")
@Data
public class OrderRepair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderId;
    private BigDecimal price;
    private String msg;
    private String status;
    private String rejectMsg;
    private Date createDate;
    private Date updateDate;
    private Integer shopAddressId;
    private String shopCode;
    private Integer shopId;
    private String code;
    private Integer userId;
}
