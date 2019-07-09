package com.wj.api.controller.user;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

@Api(value = "/v1/login", tags = "用户接口模块")
@RestController
@RequestMapping("/v1/login/")
public class TokenController {

    public final static Logger logger = LoggerFactory.getLogger(TokenController.class);

    /**
     * 获取token
     *
     * @param
     * @return String
     * @author thz
     */
    @ApiOperation(value = "获取token", notes = "获取token")
    @GetMapping("getToken")
    public ResponseMessage<String> getToken() {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setId((Integer) claims.get("userId"));
        userInfo.setUserName((String) claims.get("userName"));
        String jwtToken = JwtUtil.generateToken(userInfo);
        return ResponseMessage.ok(jwtToken);
    }

}
