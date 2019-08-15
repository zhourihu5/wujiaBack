package com.wj.core.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.order.embeddable.OrderUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;


@ApiModel(description = "用户订单关联表")
@Entity
@Data
@Table(name = "ebiz_order_user")
public class EbizOrderUser {

    @EmbeddedId
    private OrderUser orderUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;
    @Transient
    private OrderInfo orderInfo;
}
