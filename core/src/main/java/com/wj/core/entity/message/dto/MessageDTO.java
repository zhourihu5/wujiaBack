package com.wj.core.entity.message.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private String title;
    private String content;
    private String type;
    private Integer communtityId;

}
