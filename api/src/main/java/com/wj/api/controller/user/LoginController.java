package com.wj.api.controller.user;

import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.api.utils.ResultUtil;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.UserInfoDTO;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.util.mapper.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

@Api(value = "/login", tags = "用户接口模块")
@RestController
@RequestMapping("/login/")
public class LoginController {

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
    public Object sendMsg(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        SysUserInfo userInfo = userInfoService.findByName(userName);
        if (userInfo == null) {
            return ResultUtil.error(HttpServletResponse.SC_NO_CONTENT, "你不是平台用户");
        }
        if (userInfo.getFlag() != 2) {
            return ResultUtil.error(HttpServletResponse.SC_NO_CONTENT, "你不是pad端用户");
        }
        String key = request.getParameter("key");
        // 获取家庭ID
        BaseDevice baseDevice = baseDeviceService.findByKey(key);
        SysUserFamily sysUserFamily = userFamilyService.findByUidAndFid(userInfo.getId(), baseDevice.getFamilyId());
        if (sysUserFamily == null) {
            return ResultUtil.error(HttpServletResponse.SC_NO_CONTENT, "数据异常");
        }
        if (sysUserFamily.getIdentity() != 1 || sysUserFamily.getStatus() != 1) {
            return ResultUtil.error(HttpServletResponse.SC_NO_CONTENT, "账户限制");
        }
        final HttpSession httpSession = request.getSession();
        Object code = httpSession.getAttribute(userName);
        String smsCode = String.valueOf(code);
        try {
            if (code == null) {
                smsCode = CommonUtils.createRandomNum(6);// 生成随机数
                httpSession.setAttribute(userName, smsCode);
            }
            //TimerTask实现5分钟后从session中删除smsCode验证码
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    httpSession.removeAttribute(userName);
                    timer.cancel();
                    System.out.println("smsCode删除成功");
                }
            }, 5 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            // 发送请求,第三方短信通信接口参数设置：账号accName 密码accPwd  乐信短信api文档查看地址：http://www.lx598.com/apitext.html
//            responseBody = sendSms(String   accName,String accPwd,recPhoneNum,"你的短信验证码是："+captcha);
        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", smsCode);
    }

    /**
     * 登录
     *
     * @param request
     * @return List<UserInfo>
     * @author thz
     */
    @RequestMapping(value = "checking", method = RequestMethod.GET)
    public Object checking(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String smsCode = request.getParameter("smsCode");
        HttpSession httpSession = request.getSession();
//        UserInfoDTO userInfoDTO = new UserInfoDTO();
        String jwtToken = "";
        try {
            Object data = httpSession.getAttribute(userName);
            if (!String.valueOf(data).equals(smsCode)) {
                return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "验证码不正确");
            }
            SysUserInfo userInfo = userInfoService.findByName(userName);
            jwtToken = JwtUtil.generateToken(userInfo);
//            userInfoDTO = BeanMapper.map(userInfo, UserInfoDTO.class);
//            userInfoDTO.setToken("Bearer " + jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(HttpServletResponse.SC_OK, "SUCCESS", jwtToken);
    }

    /**
     * 获取token
     *
     * @param userName
     * @return String
     * @author thz
     */
    @RequestMapping(value = "getToken", method = RequestMethod.POST)
    public String login(String userName) {
        // Create Jwt token
        SysUserInfo userInfo = userInfoService.findByName(userName);
        return JwtUtil.generateToken(userInfo);
    }

}
