package com.wj.core.entity.activity;

import lombok.Data;

import javax.persistence.*;
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
    private Date startDate;
    private Date endDate;
    private Integer commodityId;
    private String commodityCode;
    private String memo;
    private String status;
    private String cover;

}
