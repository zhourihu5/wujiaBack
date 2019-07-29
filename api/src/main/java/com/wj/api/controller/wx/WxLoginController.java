package com.wj.api.controller.wx;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.service.wx.WxLoginService;
import com.wj.core.util.HttpClients;
import com.wj.core.helper.impl.RedisHelperImpl;
import com.wj.core.service.SendSms;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;

@Api(value = "/wx/login", tags = "用户接口模块")
@RestController
@RequestMapping("/wx/login/")
public class WxLoginController {

    public final static Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    @Autowired
    private WxLoginService wxLoginService;

    /**
     * 小程序微信授权
     *
     * @param code
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "小程序微信授权", notes = "小程序微信授权")
    @GetMapping("wxLogin")
    public ResponseMessage wxLogin(String code) {
        return ResponseMessage.ok(wxLoginService.wxLogin(code));
    }


}
