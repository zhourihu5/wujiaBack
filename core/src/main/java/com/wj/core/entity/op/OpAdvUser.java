package com.wj.core.entity.op;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.op.embeddable.AdvUser;
import com.wj.core.entity.op.embeddable.FamilyService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@ApiModel(description = "广告用户关联表")
@Data
@Entity
public class OpAdvUser {
    @EmbeddedId
    private AdvUser advUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

}
