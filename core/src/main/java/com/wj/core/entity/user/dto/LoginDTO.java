package com.wj.core.entity.user.dto;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.dto.DeviceDTO;
import com.wj.core.entity.base.dto.SipDTO;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel(description = "登录DTO")
@Data
public class LoginDTO {

    private SysUserInfo userInfo;

    private List<AuthorityDTO> authorityList;

    private String token;

    private DeviceDTO device;
    private SipDTO sip;

//    public SysUserInfo getUserInfo() {
//        return userInfo;
//    }
//
//    public void setUserInfo(SysUserInfo userInfo) {
//        this.userInfo = userInfo;
//    }
//
//    public List<AuthorityDTO> getAuthorityList() {
//        return authorityList;
//    }
//
//    public void setAuthorityList(List<AuthorityDTO> authorityList) {
//        this.authorityList = authorityList;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public DeviceDTO getDevice() {
//        return device;
//    }
//
//    public void setDevice(DeviceDTO device) {
//        this.device = device;
//    }
}
