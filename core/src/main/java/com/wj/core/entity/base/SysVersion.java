package com.wj.core.entity.base;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class SysVersion {

    @Id
    private Integer id;
    private Short sysVer;
    private String showVer;
    private Date createDate;
    private String url;

}
