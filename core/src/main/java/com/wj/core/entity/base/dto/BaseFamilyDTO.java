package com.wj.core.entity.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*
 * 家庭表
 */
@ApiModel(description = "家庭表")
@Data
public class BaseFamilyDTO {

    private String name;
    private List<BaseFamily> list;

}
