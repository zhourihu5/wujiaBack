package com.wj.core.entity.user.dto;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "后台登录DTO")
public class LoginDTO {

    private SysUserInfo sysUserInfo;

    private List<AuthorityDTO> authorityDTOList;

    private String token;

    public SysUserInfo getSysUserInfo() {
        return sysUserInfo;
    }

    public void setSysUserInfo(SysUserInfo sysUserInfo) {
        this.sysUserInfo = sysUserInfo;
    }

    public List<AuthorityDTO> getAuthorityDTOList() {
        return authorityDTOList;
    }

    public void setAuthorityDTOList(List<AuthorityDTO> authorityDTOList) {
        this.authorityDTOList = authorityDTOList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
