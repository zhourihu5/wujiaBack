package com.wj.api.controller.user;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.IndexDTO;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.BindingService;
import com.wj.core.service.user.UserFamilyService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "/v1/binding", tags = "微信绑定用户接口模块")
@RestController
@RequestMapping("/v1/binding/")
public class BindingController {

    @Autowired
    private BindingService bindingService;

    /**
     * 绑定用户信息
     * @param userName
     * @param wxOpenId
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "绑定用户信息", notes = "绑定用户信息")
    @PostMapping("bindingUser")
    public ResponseMessage bindingUser(String userName, String wxOpenId) {
        bindingService.bindingUser(userName, wxOpenId);
        return ResponseMessage.ok();
    }

    /**
     * 查询微信是否已经绑定用户
     * @param wxOpenId
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "查询微信是否已经绑定用户", notes = "查询微信是否已经绑定用户")
    @GetMapping("getWxOpenId")
    public ResponseMessage getWxOpenId(String wxOpenId) {
        return ResponseMessage.ok(bindingService.getWxOpenId(wxOpenId));
    }

}
