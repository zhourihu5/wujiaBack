package com.api.controller.user;

import com.api.utils.CommonUtils;
import com.api.utils.JwtUtil;
import com.api.utils.ResultUtil;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping("/login/")
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取验证码
     *
     * @param request
     * @return List<UserInfo>
     * @author thz
     */
    @RequestMapping(value = "sendMsg", method = RequestMethod.GET)
    public Object sendMsg(final HttpServletRequest request) {
        String userName = request.getParameter("userName");
        SysUserInfo userInfo = userInfoService.findUserByName(userName);
        if (userInfo == null) {
            return ResultUtil.error(HttpServletResponse.SC_NO_CONTENT, "你不是平台用户");
        }
        final HttpSession httpSession = request.getSession();
        Object data = httpSession.getAttribute(userName);
        String smsCode = String.valueOf(data);
        try {
            if (data == null) {
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
            }, 1 * 60 * 1000);
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
    public Object checking(final HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String smsCode = request.getParameter("smsCode");
        final HttpSession httpSession = request.getSession();
        String jwtToken = "";
        try {
            Object data = httpSession.getAttribute(userName);
            if (!String.valueOf(data).equals(smsCode)) {
                return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "验证码不正确");
            }
            SysUserInfo userInfo = userInfoService.findUserByName(userName);
            jwtToken = JwtUtil.generateToken(userInfo);
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
        SysUserInfo userInfo = userInfoService.findUserByName(userName);
        return JwtUtil.generateToken(userInfo);
    }


}
