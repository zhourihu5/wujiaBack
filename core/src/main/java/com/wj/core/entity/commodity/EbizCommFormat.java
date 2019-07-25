package com.wj.core.entity.commodity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EbizCommFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String formatName;
    private String formatValue;
    private Integer commodityId;

}
