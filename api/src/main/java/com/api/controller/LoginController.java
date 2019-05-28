package com.api.controller;

import com.api.entity.UserInfo;
import com.api.service.UserInfoService;
import com.api.utils.CommonUtils;
import com.api.utils.JwtUtil;
import com.api.utils.ResultUtil;
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
     * @param request
     * @return List<UserInfo>
     * @author thz
     */
    @RequestMapping(value = "sendMsg", method = RequestMethod.GET)
    public Object sendMsg(final HttpServletRequest request) {
        String phone = request.getParameter("phone");
        UserInfo userInfo = userInfoService.findUserByName(phone);
        if (userInfo == null) {
            return ResultUtil.error(HttpServletResponse.SC_FOUND, "");
        }
        final HttpSession httpSession = request.getSession();
        Object data = httpSession.getAttribute(phone);
        String smsCode = "";
        try {
            if (data == null) {
                smsCode = CommonUtils.createRandomNum(6);// 生成随机数
                httpSession.setAttribute(phone, smsCode);
            }
            //TimerTask实现5分钟后从session中删除smsCode验证码
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    httpSession.removeAttribute(phone);
                    timer.cancel();
                    System.out.println("smsCode删除成功");
                }
            }, 1 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            // 发送请求,第三方短信通信接口参数设置：账号accName 密码accPwd  乐信短信api文档查看地址：http://www.lx598.com/apitext.html
//            responseBody = sendSms(String   accName,String accPwd,recPhoneNum,"你的短信验证码是："+captcha);
        return smsCode;
    }

    /**
     * 获取token
     * @param userName
     * @return String
     * @author thz
     */
    @RequestMapping(value = "getToken", method = RequestMethod.POST)
    public String login(String userName) {
        // Create Jwt token
        return JwtUtil.generateToken(userName);
    }

    /**
     * 登录
     * @param request
     * @return List<UserInfo>
     * @author thz
     */
    @RequestMapping(value = "checking", method = RequestMethod.GET)
    public Object checking(final HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String smsCode = request.getParameter("smsCode");
        final HttpSession httpSession = request.getSession();
        try {
            Object data = httpSession.getAttribute(phone);
            if (data != smsCode) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//            // 发送请求,第三方短信通信接口参数设置：账号accName 密码accPwd  乐信短信api文档查看地址：http://www.lx598.com/apitext.html
//            responseBody = sendSms(String   accName,String accPwd,recPhoneNum,"你的短信验证码是："+captcha);
        return "success";
    }


}
