package com.wj.core.entity.base.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "登录设备专用")
public class DeviceDTO {

    @ApiModelProperty(value = "pad标号(01 02 03 04...)")
    private String buttonKey;
    @ApiModelProperty(value = "底座机器唯一标识/编号")
    private String deviceKey;

    public String getButtonKey() {
        return buttonKey;
    }

    public void setButtonKey(String buttonKey) {
        this.buttonKey = buttonKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }
}
