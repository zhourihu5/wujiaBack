package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 屏幕保护表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "屏幕保护表")
@Entity
public class SysScreen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "封面")
    private String cover;
    @ApiModelProperty(value = "路径")
    private String url;
    @ApiModelProperty(value = "社区ID")
    private Integer communtityId;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(Integer communtityId) {
        this.communtityId = communtityId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
