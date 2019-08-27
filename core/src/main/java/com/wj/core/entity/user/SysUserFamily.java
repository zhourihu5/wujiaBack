package com.wj.core.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.embeddable.UserFamily;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户家庭关联表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户家庭关联表")
@Entity
@Data
@Table(name = "sys_user_family")
public class SysUserFamily {

    @EmbeddedId
    private UserFamily userFamily;
    @ApiModelProperty(value = "1、房产所有人 2、使用人")
    private Integer identity;

}
