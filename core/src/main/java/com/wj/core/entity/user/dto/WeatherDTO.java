package com.wj.core.entity.user.dto;

import com.wj.core.entity.user.SysRestrict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 天气和限行表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "天气和限行表")
public class WeatherDTO {
    @ApiModelProperty(value = "天气")
    private Object weather;
    @ApiModelProperty(value = "限行")
    private SysRestrict restrict;
    @ApiModelProperty(value = "更新token")
    private String token;

    public Object getWeather() {
        return weather;
    }

    public void setWeather(Object weather) {
        this.weather = weather;
    }

    public SysRestrict getRestrict() {
        return restrict;
    }

    public void setRestrict(SysRestrict restrict) {
        this.restrict = restrict;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
