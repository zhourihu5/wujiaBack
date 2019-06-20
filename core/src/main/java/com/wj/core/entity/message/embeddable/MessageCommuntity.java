package com.wj.core.entity.message.embeddable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;


@ApiModel(description = "消息社区关联表")
@Data
@Embeddable
public class MessageCommuntity implements Serializable {

    private Integer messageId;
    private Integer communtityId;


}
