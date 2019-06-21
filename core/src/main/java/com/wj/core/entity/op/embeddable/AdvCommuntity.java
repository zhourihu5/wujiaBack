package com.wj.core.entity.op.embeddable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;


@ApiModel(description = "广告社区关联")
@Data
@Embeddable
public class AdvCommuntity implements Serializable {
    private Integer advId;
    private Integer communtityId;


}
