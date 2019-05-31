package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 消息表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "消息表")
@Entity
public class SysMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "消息内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    private Date create_date;
    @ApiModelProperty(value = "类型 1.物业  2.社区公告 3.系统通知")
    private Integer type;
    @ApiModelProperty(value = "消息标题")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
