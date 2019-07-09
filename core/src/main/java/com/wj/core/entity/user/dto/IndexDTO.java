package com.wj.core.entity.user.dto;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "用户DTO")
public class IndexDTO {

    private SysUserInfo userInfo;

    private List<SysUserInfo> userInfoList;

    private BaseCommuntity communtity;

    public SysUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SysUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysUserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<SysUserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public BaseCommuntity getCommuntity() {
        return communtity;
    }

    public void setCommuntity(BaseCommuntity communtity) {
        this.communtity = communtity;
    }

}
