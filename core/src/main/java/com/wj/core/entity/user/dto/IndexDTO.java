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

    private SysUserInfo sysUserInfo;

    private List<SysUserInfo> sysUserInfoList;

    private Object weather;

    private BaseCommuntity communtity;

    private List<BaseDevice> baseDeviceList;

    public SysUserInfo getSysUserInfo() {
        return sysUserInfo;
    }

    public void setSysUserInfo(SysUserInfo sysUserInfo) {
        this.sysUserInfo = sysUserInfo;
    }

    public List<SysUserInfo> getSysUserInfoList() {
        return sysUserInfoList;
    }

    public void setSysUserInfoList(List<SysUserInfo> sysUserInfoList) {
        this.sysUserInfoList = sysUserInfoList;
    }

    public Object getWeather() {
        return weather;
    }

    public void setWeather(Object weather) {
        this.weather = weather;
    }

    public BaseCommuntity getCommuntity() {
        return communtity;
    }

    public void setCommuntity(BaseCommuntity communtity) {
        this.communtity = communtity;
    }

    public List<BaseDevice> getBaseDeviceList() {
        return baseDeviceList;
    }

    public void setBaseDeviceList(List<BaseDevice> baseDeviceList) {
        this.baseDeviceList = baseDeviceList;
    }
}
