package com.wj.core.entity.user.dto;

import com.wj.core.entity.base.dto.DeviceDTO;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@ApiModel(description = "绑定")
public class BindingDTO {
    private String userName;
    private String smsCode;
    private String cover;
    private String nickName;
    private String openid;
}
