package com.wj.core.entity.commodity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.atta.AttaInfo;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "ebiz_commodity")
@Data
@Entity
public class Commodity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String code;
    private String shopCode;
    private Integer userId;
    private String infos;
    private String safe;
    private String isFree;
    private String status;
    private String labelsName;
    private Integer salesNum;
    private String isSafe;
    private String labelsCode;
    private Integer repertoryNum;
    private BigDecimal initPrice;
    private BigDecimal price;
    private String formatVal;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    @Transient
    private String[] formatVals;
    @Transient
    List<AttaInfo> attaInfoList;
}
