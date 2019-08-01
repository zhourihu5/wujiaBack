package com.wj.core.entity.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ebiz_apply_unlock")
@Data
public class ApplyUnlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "申请人姓名")
    private String applyName;
    @ApiModelProperty(value = "业主")
    private String ownName;
    @ApiModelProperty(value = "关系")
    private String relation;
    @ApiModelProperty(value = "审核是否通过 0.待审核 1.通过 2.不通过")
    private String status;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String create_date;
    @ApiModelProperty(value = "申请人id")
    private String userId;
    @ApiModelProperty(value = "家庭id")
    private String familyId;

}
