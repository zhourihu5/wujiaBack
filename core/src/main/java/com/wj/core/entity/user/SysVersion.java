package com.wj.core.entity.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String sysVer;
    private String showVer;
    private Date createDate;
    private String versionDesc;

}
