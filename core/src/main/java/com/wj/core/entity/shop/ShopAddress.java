package com.wj.core.entity.shop;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ebiz_shop_address")
@Data
public class ShopAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String shopCode;
    private String address;
    private String province;
    private String city;
    private String area;
    private Date createDate;
    private String consignee;
    private String phone;
    private String isDef;

}
