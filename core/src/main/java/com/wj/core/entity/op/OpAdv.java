package com.wj.core.entity.op;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "社区ID")
    private String communtityId;
    @ApiModelProperty(value = "封面")
    private String cover;
    @ApiModelProperty(value = "地址")
    private String url;
    @ApiModelProperty(value = "开始时间")
    private Integer startDate;
    @ApiModelProperty(value = "结束时间")
    private Integer endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(String communtityId) {
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

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }
}
