package com.wj.core.entity.message.dto;

import com.wj.core.entity.message.Message;
import lombok.Data;

import java.util.List;

@Data
public class MessageTypeDTO {

    private String typeNo;
    private String typeName;
    private Integer unReadNum;
    private List<Message> unReadList;

}
