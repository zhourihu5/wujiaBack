package com.wj.core.entity.user.dto;

import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 服务表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "服务表")
public class ServiceAllDTO {

    @ApiModelProperty(value = "轮播图")
    private List<OpBanner> bannerList;
    @ApiModelProperty(value = "服务列表")
    private List<ServiceDTO> serviceList;

    public List<OpBanner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<OpBanner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<ServiceDTO> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }
}
