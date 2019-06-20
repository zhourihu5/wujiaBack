package com.wj.core.entity.message.embeddable;

import io.swagger.annotations.ApiModel;

import javax.persistence.Embeddable;
import java.io.Serializable;


@ApiModel(description = "消息用户关联表")
@Data
@Embeddable
public class MessageUser implements Serializable {

    private Integer messageId;
    private Integer userId;


}
