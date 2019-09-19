package com.wj.core.entity.activity.dto;

import com.wj.core.entity.address.Address;
import com.wj.core.entity.commodity.Commodity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ActivityDTO {

    private Integer id;
    private String title;
    private Integer communityId;
    private String communityCode;
    private Date startDate;
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
    private String province;
    private String city;
    private String area;
    private String remark;
    private String padCover;
    private String giftImg;
    private Date createDate;
    @Transient
    private Commodity commodity;
    @Transient
    private Address address;
    @Transient
    private Integer isJoin;
    @Transient
    private String largeMoney;
    @Transient
    private BigDecimal paymentMoney;
    @Transient
    private String saleTip;
    @Transient
    private Integer platformCouponCount;
    @Transient
    private Integer activityCouponCount;

}
