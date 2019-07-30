package com.wj.api.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.BindingDTO;
import com.wj.core.entity.user.dto.IndexDTO;
import com.wj.core.entity.user.dto.LoginDTO;
import com.wj.core.entity.user.dto.XcxLoginDTO;
import com.wj.core.helper.impl.RedisHelperImpl;
import com.wj.core.service.SendSms;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.address.AddressService;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.BindingService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.service.wx.WxLoginService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Api(value = "/wx/binding", tags = "微信绑定用户接口模块")
@RestController
@RequestMapping("/wx/binding/")
public class BindingController {

    public final static Logger logger = LoggerFactory.getLogger(BindingController.class);

    @Autowired
    private BindingService bindingService;

    @Autowired
    private WxLoginService wxLoginService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisHelperImpl redisHelper;

    @Autowired
    private SendSms sendSms;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private AddressService addressService;

    /**
     * 绑定用户信息
     *
     * @param bindingDTO
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "绑定用户信息", notes = "绑定用户信息")
    @PostMapping("bindingUser")
    public ResponseMessage<XcxLoginDTO> bindingUser(@RequestBody BindingDTO bindingDTO) {
        Object data = redisHelper.getValue(bindingDTO.getUserName());
        // TODO 测试期间0000放行
        if (!bindingDTO.getSmsCode().equals("0000") && StringUtils.isNotBlank(bindingDTO.getSmsCode())) {
            if (!String.valueOf(data).equals(bindingDTO.getSmsCode())) {
                throw new ServiceException("验证码不正确", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
        bindingService.bindingUser(bindingDTO.getUserName(), bindingDTO.getCover(), bindingDTO.getNickName(), bindingDTO.getOpenid());
        SysUserInfo userInfo = userInfoService.findByName(bindingDTO.getUserName());
        List<Activity> activityList = activityService.findList(userInfo.getId());
        String jwtToken = JwtUtil.generateToken(userInfo);
        XcxLoginDTO loginDTO = new XcxLoginDTO();
        loginDTO.setToken(jwtToken);
        loginDTO.setUserInfo(userInfo);
        loginDTO.setActivityList(activityList);
        loginDTO.setCommuntityName(addressService.findCommuntityNameByUserId(userInfo.getId()));
        return ResponseMessage.ok(loginDTO);
    }

    /**
     * 查询微信是否已经绑定用户
     *
     * @param code
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "查询微信是否已经绑定用户", notes = "查询微信是否已经绑定用户")
    @GetMapping("checkBinding")
    public ResponseMessage<XcxLoginDTO> getWxOpenId(String code) {
        Object object = wxLoginService.wxLogin(code);
        JSONObject json = JSON.parseObject(object.toString());
        String openid = json.getString("openid");
        String session_key = json.getString("session_key");
        SysUserInfo userInfo = new SysUserInfo();
        XcxLoginDTO loginDTO = new XcxLoginDTO();
        Integer userId = 0;
        if (openid != null) {
            userInfo = bindingService.findByOpenId(openid);
            if (userInfo != null) {
                String jwtToken = JwtUtil.generateToken(userInfo);
                loginDTO.setToken(jwtToken);
                loginDTO.setUserInfo(userInfo);
                loginDTO.setCommuntityName(addressService.findCommuntityNameByUserId(userInfo.getId()));
                userId = userInfo.getId();
            }
            loginDTO.setOpenid(openid);
        }
        List<Activity> activityList = activityService.findList(userId);
        loginDTO.setActivityList(activityList);

        return ResponseMessage.ok(loginDTO);
    }

    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @GetMapping("sendMsg")
    public ResponseMessage sendMsg(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        logger.info(userName + "发送验证码info");
        SysUserInfo userInfo = userInfoService.findByName(userName);
        if (userInfo == null) {
            throw new ServiceException("你不是平台用户", ErrorCode.INTERNAL_SERVER_ERROR);
        }

        Object code = redisHelper.getValue(userName);
        String smsCode = String.valueOf(code);
        try {
            if (code == null) {
                smsCode = CommonUtils.createRandomNum(6);// 生成随机数
                redisHelper.valuePut(userName, smsCode);
            }
            // 发送验证码
//            String message = sendSms.send(userName, smsCode);
            //TimerTask实现5分钟后从session中删除smsCode验证码
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    redisHelper.remove(userName);
                    timer.cancel();
                    logger.info(userName + "的验证码已失效");
                }
            }, 5 * 60 * 1000);
//            return ResponseMessage.ok();
            return ResponseMessage.ok(smsCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.ok();
    }

}
