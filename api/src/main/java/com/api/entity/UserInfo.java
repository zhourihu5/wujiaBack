package com.api.entity;

/**
 * 用户表
 *
 * @author thz
 * @version 1.0
 */
public class UserInfo {

    private int id;
    // 用户名称
    private String userName;
    // 用户密码
    private String password;
    // 用户昵称
    private String nickName;
    // 头像
    private String icon;
    // 用户状态
    private String status;
    // 标识 1、后台用户 2、ipad用户 3、app用户
    private int flag;
    // 社区标识
    private int communtityId;
    // 扩展1
    private String extend1;
    // 扩展2
    private String extend2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(int communtityId) {
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
}
