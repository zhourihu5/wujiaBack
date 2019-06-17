package com.wj.core.entity.card;

import com.wj.core.entity.card.enums.CardType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity(name = "base_pad_module")
public class PadModule {

    @Id
    private Integer id;
    private String name;
    private Integer lev;
    private Integer parentId;
    private String flag;
    @Enumerated(EnumType.ORDINAL)
    private CardType type;
    private String icon;


}
