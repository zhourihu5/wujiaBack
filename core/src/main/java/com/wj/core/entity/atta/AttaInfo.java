package com.wj.core.entity.atta;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_atta_info")
public class AttaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer objectId;
    private String objectType;
    private String attaAddr;
    private String attaName;
    private String type;
    private Date createDate;
    private String remark;

}
