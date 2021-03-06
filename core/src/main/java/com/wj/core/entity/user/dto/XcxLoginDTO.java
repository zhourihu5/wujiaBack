package com.wj.core.entity.user.dto;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.dto.DeviceDTO;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@ApiModel(description = "小程序登录DTO")
public class XcxLoginDTO {

    private SysUserInfo userInfo;

    private String openid;

    private String token;

    private List<Activity> activityList;

    private String communtityName;

    private String isBindingFamily;

    private ApplyLock applyLock;

    private List<BaseCommuntity> communtityList;

    private List<OrderInfo> list;

    private List<BaseFamily> familyList;

    private boolean unRead;

}
