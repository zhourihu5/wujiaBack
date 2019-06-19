package com.wj.core.entity.op;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 广告表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "广告表")
@Entity
public class OpAdv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "社区ID")
    private Integer communtityId;
    @ApiModelProperty(value = "封面")
    private String cover;
    @ApiModelProperty(value = "地址")
    private String url;
    @ApiModelProperty(value = "开始时间")
    private Date startDate;
    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(Integer communtityId) {
        this.communtityId = communtityId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
