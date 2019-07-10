package com.wj.core.entity.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wj.core.util.time.DateJsonSerializer;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity(name = "sys_message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date createDate;
    @Transient
    private Integer isRead;
    @Transient
    private String communtity;

}
