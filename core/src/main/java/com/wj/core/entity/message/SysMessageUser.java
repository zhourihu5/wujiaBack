package com.wj.core.entity.message;

import com.wj.core.entity.message.embeddable.MessageCommuntity;
import com.wj.core.entity.message.embeddable.MessageUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
@ApiModel(description = "消息用户关联表")
@Data
@Entity(name = "sys_message_user")
public class SysMessageUser {

    @EmbeddedId
    private MessageUser messageUser;


}
