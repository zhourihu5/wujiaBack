package com.wj.core.entity.card.dto;

import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.card.enums.CardType;
import lombok.Data;

import java.util.Date;

@Data
public class CardDTO {

    private Integer id;
    private String title;
    private String memo;
    private CardType type;
    private String url;
    private String icon;
    private CardStatus isShow;

}
