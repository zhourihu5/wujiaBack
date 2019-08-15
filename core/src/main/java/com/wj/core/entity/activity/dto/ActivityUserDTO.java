package com.wj.core.entity.activity.dto;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.card.dto.CardServicesDTO;
import com.wj.core.entity.user.SysUserInfo;
import lombok.Data;

import java.util.List;

@Data
public class ActivityUserDTO {

    private Activity activity;
    private List<SysUserInfo> userInfoList;

}
