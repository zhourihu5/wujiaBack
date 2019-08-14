package com.wj.core.entity.order;

import com.wj.core.entity.order.embeddable.OrderUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;


@ApiModel(description = "用户订单关联表")
@Entity
@Data
@Table(name = "ebiz_order_user")
public class EbizOrderUser {

    @EmbeddedId
    private OrderUser orderUser;
    @Transient
    private OrderInfo orderInfo;
}
