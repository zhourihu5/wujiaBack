package com.wj.core.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@ApiModel(description = "用户订单关联表")
@Entity
@Data
@Table(name = "ebiz_order_user")
public class OrderUser {

//    @EmbeddedId
//    private OrderUser1 orderUser;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer orderId;
    private String orderCode;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;
    @Transient
    private OrderInfo orderInfo;
}
