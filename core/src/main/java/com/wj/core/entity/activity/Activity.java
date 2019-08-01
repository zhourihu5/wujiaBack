package com.wj.core.entity.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.commodity.Commodity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ebiz_activity")
@Data
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer communityId;
    private String communityCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;
    private Integer commodityId;
    private String commodityCode;
    private String memo;
    private String status;
    private String cover;
    private String isShow;
    private String saleRules;
    private String saleType;
    private Integer deliveryHour;
    private BigDecimal price;
    private String declare;
    private String province;
    private String city;
    private String area;
    @Transient
    private Commodity commodity;
    @Transient
    private Address address;
    @Transient
    private Integer isJoin;
    @Transient
    private String largeMoney;

}
