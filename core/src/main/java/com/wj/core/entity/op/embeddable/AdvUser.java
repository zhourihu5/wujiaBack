package com.wj.core.entity.op.embeddable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;


@ApiModel(description = "广告用户关联")
@Data
@Embeddable
public class AdvUser implements Serializable {
    private Integer advId;
    private Integer userId;

}
