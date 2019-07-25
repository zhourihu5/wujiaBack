package com.wj.core.entity.shop;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ebiz_shop")
@Data
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String code;
    private String phone;
    private String cover;
    private String userName;
    private String shortName;
    private Integer userId;
    private Date createDate;
    private Integer communityId;
    private String communityCode;

}
