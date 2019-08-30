package com.wj.core.entity.experience.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class ExperienceDTO {

    private Integer id;
    @ApiModelProperty(value = "体验券名称")
    private String name;
    @ApiModelProperty(value = "社区ID,号分隔")
    private String communitys;
    @ApiModelProperty(value = "社区name")
    private String communityNames;
    @ApiModelProperty(value = "活动banner")
    private String banner;
    @ApiModelProperty(value = "详情图片")
    private String cover;
    @ApiModelProperty(value = "每人限领次数")
    private Integer limitNum;
    @ApiModelProperty(value = "领取方式  1主动领取 2自动发放")
    private String receive;
    @ApiModelProperty(value = "体验卷发放总数量")
    private Integer count;
    @ApiModelProperty(value = "规则")
    private String rule;
    @ApiModelProperty(value = "体验券图片")
    private String img1;
    @ApiModelProperty(value = "体验券图片")
    private String img2;
    @ApiModelProperty(value = "体验券图片")
    private String img3;
    @ApiModelProperty(value = "体验券图片")
    private String img4;
    @ApiModelProperty(value = "体验券图片")
    private String img5;
    @ApiModelProperty(value = "状态 0.未发放 1.已发放")
    private String status;
    @ApiModelProperty(value = "9删除 0.未上架 1.上架")
    private String isShow;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date updateDate;
    @Transient
    private String[] experienceCodes;

}
