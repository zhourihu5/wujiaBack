package com.wj.api.controller.user;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.api.utils.ResultUtil;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.UserInfoDTO;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.util.mapper.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

@Api(value = "/login", tags = "用户接口模块")
@RestController
@RequestMapping("/login/")
public class LoginController {

    public final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BaseDeviceService baseDeviceService;

    @Autowired
    private UserFamilyService userFamilyService;


    /**
     * 获取验证码
     *
     * @param request
     * @return List<UserInfo>
     * @author thz
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("sendMsg")
    public ResponseMessage<String> sendMsg(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        logger.info(userName+"发送验证码info");
        SysUserInfo userInfo = userInfoService.findByName(userName);
        if (userInfo == null) {
            throw new ServiceException("你不是平台用户", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (userInfo.getFlag() != 2) {
            throw new ServiceException("你不是pad端用户", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        String key = request.getParameter("key");
        // 获取家庭ID
        BaseDevice baseDevice = baseDeviceService.findByKey(key);
        if (baseDevice == null) {
            throw new ServiceException("数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        SysUserFamily sysUserFamily = userFamilyService.findByUidAndFid(userInfo.getId(), baseDevice.getFamilyId());
        if (sysUserFamily == null) {
            throw new ServiceException("数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (sysUserFamily.getIdentity() != 1 || sysUserFamily.getStatus() != 1) {
            throw new ServiceException("账户限制", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        final HttpSession httpSession = request.getSession();
        Object code = httpSession.getAttribute(userName);
        String smsCode = String.valueOf(code);
        try {
            if (code == null) {
                smsCode = CommonUtils.createRandomNum(6);// 生成随机数
                smsCode = "123456";
                httpSession.setAttribute(userName, smsCode);
            }
            //TimerTask实现5分钟后从session中删除smsCode验证码
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    httpSession.removeAttribute(userName);
                    timer.cancel();
                    logger.info(userName + "的验证码已失效");
                }
            }, 5 * 60 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
//            // 发送请求,第三方短信通信接口参数设置：账号accName 密码accPwd  乐信短信api文档查看地址：http://www.lx598.com/apitext.html
//            responseBody = sendSms(String   accName,String accPwd,recPhoneNum,"你的短信验证码是："+captcha);
//        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", smsCode);
        return ResponseMessage.ok(smsCode);
    }

    /**
     * 登录
     *
     * @param request
     * @return List<UserInfo>
     * @author thz
     */
    @ApiOperation(value = "登录", notes = "登录")
    @GetMapping("checking")
    public ResponseMessage<String> checking(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String smsCode = request.getParameter("smsCode");
        HttpSession httpSession = request.getSession();
        String jwtToken = "";
        try {
            Object data = httpSession.getAttribute(userName);
            if (!String.valueOf(data).equals(smsCode)) {
                throw new ServiceException("验证码不正确", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            SysUserInfo userInfo = userInfoService.findByName(userName);
            jwtToken = JwtUtil.generateToken(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.ok(jwtToken);
    }


}
