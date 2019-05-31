package com.wj.core.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户表")
@Entity
public class SysUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    // 用户名称
    @ApiModelProperty(value = "用户名称")
    private String userName;
    // 用户密码
    @ApiModelProperty(value = "用户名称")
    private String password;
    // 用户昵称
    @ApiModelProperty(value = "用户名称")
    private String nickName;
    // 头像
    @ApiModelProperty(value = "用户头像")
    private String icon;
    // 用户状态
    @ApiModelProperty(value = "用户状态")
    private Integer status;
    // 标识 1、后台用户 2、ipad用户 3、app用户
    @ApiModelProperty(value = "标识 1、后台用户 2、ipad用户 3、app用户")
    private Integer flag;
    // 社区标识
    @ApiModelProperty(value = "社区标识")
    private Integer communtityId;
    // 创建时间
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    // 扩展1
    @ApiModelProperty(value = "扩展1")
    private String extend1;
    // 扩展2
    @ApiModelProperty(value = "扩展2")
    private String extend2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(Integer communtityId) {
        this.communtityId = communtityId;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
