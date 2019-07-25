package com.wj.core.entity.base.dto;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserInfo;
import lombok.Data;

@Data
public class FamilyBindInfoDTO {

    private BaseDevice padDevice;
    private BaseDevice pedestalDevice;
    private SysUserInfo user;

}
