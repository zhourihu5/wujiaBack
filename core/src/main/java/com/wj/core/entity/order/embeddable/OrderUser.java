package com.wj.core.entity.order.embeddable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;


@ApiModel(description = "用户订单关联表")
@Data
@Embeddable
public class OrderUser implements Serializable {

    private Integer userId;
    private Integer orderId;
    private String orderCode;
    private String status;

}
