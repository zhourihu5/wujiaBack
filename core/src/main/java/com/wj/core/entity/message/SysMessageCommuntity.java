package com.wj.core.entity.message;

import com.wj.core.entity.message.embeddable.MessageCommuntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
@ApiModel(description = "消息社区关联表")
@Data
@Entity(name = "sys_message_communtity")
public class SysMessageCommuntity {

    @EmbeddedId
    private MessageCommuntity messageCommuntity;

}
