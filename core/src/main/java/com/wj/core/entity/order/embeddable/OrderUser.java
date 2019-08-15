package com.wj.core.entity.order.embeddable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


@ApiModel(description = "用户订单关联表")
@Data
@Embeddable
public class OrderUser implements Serializable {

    private Integer userId;
    private Integer orderId;
    private String orderCode;
    private String status;

}
