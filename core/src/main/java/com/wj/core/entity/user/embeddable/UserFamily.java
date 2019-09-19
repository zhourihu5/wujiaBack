package com.wj.core.entity.user.embeddable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 用户家庭关联表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户家庭关联表")
@Data
@Embeddable
public class UserFamily implements Serializable {

    private Integer userId;
    private Integer familyId;
    @Transient
    private String userIds;

}
