package com.wj.core.entity.base.dto;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserInfo;
import lombok.Data;

import java.util.List;

@Data
public class FamilyBindInfoDTO {

    private BaseDevice padDevice;
    private BaseDevice pedestalDevice;
    private SysUserInfo user;
    private List<SysUserInfo> userInfoList;

}
