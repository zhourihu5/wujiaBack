package com.wj.core.entity.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.card.enums.CardType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by sun on 2019/5/30.
 */
@Data
@Entity
public class OpCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String memo;
    @Enumerated(EnumType.ORDINAL)
    private CardType type;
    private String url;
    private String icon;
    private Integer location;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private String content;
    @OneToMany(mappedBy = "opCard", fetch = FetchType.LAZY)
    private List<UserCard> userCards;
    private String services;
    private CardStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pushDate;

}
