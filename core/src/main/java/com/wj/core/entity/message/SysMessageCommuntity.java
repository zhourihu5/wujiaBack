package com.wj.core.entity.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.message.embeddable.MessageCommuntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@ApiModel(description = "消息社区关联表")
@Data
@Entity(name = "sys_message_communtity")
public class SysMessageCommuntity {

    @EmbeddedId
    private MessageCommuntity messageCommuntity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
