package com.wj.core.entity.message.embeddable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;


@ApiModel(description = "消息用户关联表")
@Data
@Embeddable
public class MessageUser implements Serializable {

    private Integer messageId;
    private Integer userId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
