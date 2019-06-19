package com.wj.core.entity.user.dto;

import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

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
    private Page<OpService> page;

    public List<OpBanner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<OpBanner> bannerList) {
        this.bannerList = bannerList;
    }

    public Page<OpService> getPage() {
        return page;
    }

    public void setPage(Page<OpService> page) {
        this.page = page;
    }
}
