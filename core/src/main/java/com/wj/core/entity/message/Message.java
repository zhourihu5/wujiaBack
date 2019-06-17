package com.wj.core.entity.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.message.enums.MessageStatus;
import com.wj.core.entity.message.enums.MessageType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity(name = "sys_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    @Enumerated(EnumType.ORDINAL)
    private MessageType type;
    private Integer userId;
    @Enumerated(EnumType.ORDINAL)
    private MessageStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

}
