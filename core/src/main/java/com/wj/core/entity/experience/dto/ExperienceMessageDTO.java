package com.wj.core.entity.experience.dto;

import com.wj.core.entity.experience.ExperienceCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

@Data
public class ExperienceMessageDTO {
    private ExperienceCode experienceCode;
    private Boolean flag;
    private Boolean isReceive;
}
