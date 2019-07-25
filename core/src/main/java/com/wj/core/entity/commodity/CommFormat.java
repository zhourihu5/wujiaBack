package com.wj.core.entity.commodity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "ebiz_comm_format")
@Data
@Entity
public class CommFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String formatName;
    private String formatValue;
    private Integer commodityId;

}
