package com.wj.admin.controller.auth;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.AesUtils;
import com.wj.admin.utils.CommonUtils;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.auth.AuthorityService;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

@Api(value = "/auth", tags = "权限接口模块")
@RestController
@RequestMapping("/auth/")
public class AuthorityController {

    public final static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private AuthorityService authorityService;




}
