package com.wj.core.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.card.UserCard;
import com.wj.core.entity.op.OpService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户表")
@Data
public class SysUserInfoDTO {
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户名称")
    private String password;
    @ApiModelProperty(value = "用户名称")
    private String nickName;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "用户状态")
    private Integer status;
    @ApiModelProperty(value = "标识 1、后台用户 2、ipad用户 3、app用户")
    private Integer flag;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "社区ID")
    @Transient
    private Integer communtityId;
    @ApiModelProperty(value = "家庭ID")
    @Transient
    private Integer fid;
    @ApiModelProperty(value = "1、房产所有人 2、使用人")
    @Transient
    private Integer identity;



}
