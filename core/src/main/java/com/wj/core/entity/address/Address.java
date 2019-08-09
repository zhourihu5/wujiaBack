package com.wj.core.entity.address;

import com.wj.core.entity.commodity.Commodity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ebiz_address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String sex;
    private String address;
    private String province;
    private String city;
    private String area;
    private String status;
    private Integer userId;
    private Integer communtityId;
    private String communtityName;
}
