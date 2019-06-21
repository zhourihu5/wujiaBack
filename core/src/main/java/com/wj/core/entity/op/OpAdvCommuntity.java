package com.wj.core.entity.op;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.core.entity.op.embeddable.AdvCommuntity;
import com.wj.core.entity.op.embeddable.AdvUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;


@ApiModel(description = "广告社区关联表")
@Data
@Entity
public class OpAdvCommuntity {
    @EmbeddedId
    private AdvCommuntity advCommuntity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

}
