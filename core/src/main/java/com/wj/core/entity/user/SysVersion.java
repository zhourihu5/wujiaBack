package com.wj.core.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 版本信息表
 * @author thz
 * @version 1.0
 */
@Data
@Entity
public class SysVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer sysVer;
    private String showVer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private String versionDesc;
    private String url;
    @Transient
    private String communtityId;

}
