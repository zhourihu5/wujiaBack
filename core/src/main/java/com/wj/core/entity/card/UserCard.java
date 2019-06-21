package com.wj.core.entity.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.user.SysUserInfo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "op_user_card")
public class UserCard implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private SysUserInfo userInfo;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "card_id")
    private OpCard opCard;

    @Enumerated(EnumType.ORDINAL)
    private CardStatus isShow;



}
