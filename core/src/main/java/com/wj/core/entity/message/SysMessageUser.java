package com.wj.core.entity.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.message.embeddable.MessageUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@ApiModel(description = "消息用户关联表")
@Entity(name = "sys_message_user")
public class SysMessageUser {

    @EmbeddedId
    private MessageUser messageUser;
    private Integer isRead;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    public MessageUser getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(MessageUser messageUser) {
        this.messageUser = messageUser;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
