package com.wj.core.entity.commodity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EbizCommodityRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String cover;
    private String price;
    private Integer storeNum;
    private String code;
    private String parentId;
    private String oldStoreNum;
    private String salesNum;
    private String weight;
    private Integer initPrice;
}
