package com.wj.api.controller.user;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.api.utils.ResultUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.dto.DeviceDTO;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.LoginDTO;
import com.wj.core.entity.user.dto.UserInfoDTO;
import com.wj.core.helper.impl.RedisHelperImpl;
import com.wj.core.service.SendSms;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
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
import java.util.List;
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

    @Autowired
    private RedisHelperImpl redisHelper;

    @Autowired
    private BaseFamilyService baseFamilyService;

    @Autowired
    private SendSms sendSms;

    /**
     * 获取验证码
     *
     * @param request
     * @return List<UserInfo>
     * @author thz
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("sendMsg")
    public ResponseMessage sendMsg(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        logger.info(userName + "发送验证码info");
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
            throw new ServiceException("此设备没有绑定家庭", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        SysUserFamily sysUserFamily = userFamilyService.findByUidAndFid(userInfo.getId(), baseDevice.getFamilyId());
        if (sysUserFamily == null) {
            throw new ServiceException("家庭成员数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (sysUserFamily.getIdentity() != 1) {
            throw new ServiceException("账户限制", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        final HttpSession httpSession = request.getSession();
        Object code = httpSession.getAttribute(userName);
        String smsCode = String.valueOf(code);
        try {
            if (code == null) {
                smsCode = CommonUtils.createRandomNum(6);// 生成随机数
                redisHelper.valuePut(userName, smsCode);
            }
            // 发送验证码
            String message = sendSms.send(userName, smsCode);
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
            return ResponseMessage.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.ok();
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
    public ResponseMessage<LoginDTO> checking(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String smsCode = request.getParameter("smsCode");
        LoginDTO loginDTO = new LoginDTO();
        Object data = redisHelper.getValue(userName);
        if (!String.valueOf(data).equals(smsCode)) {
            throw new ServiceException("验证码不正确", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        String key = request.getParameter("key");
        if (key == null) {
            throw new ServiceException("设备编号异常", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        // 获取家庭ID
        BaseDevice baseDevice = baseDeviceService.findByKey(key);
        if (baseDevice == null) {
            throw new ServiceException("此设备没有绑定家庭", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        List<BaseDevice> baseDeviceList = baseDeviceService.findByFamilyId(baseDevice.getFamilyId());
        DeviceDTO deviceDTO = new DeviceDTO();
        baseDeviceList.forEach(BaseDevice -> {
            if (BaseDevice.getFlag() == 1) {
                // 1是底座 key
                deviceDTO.setDeviceKey(BaseDevice.getDeviceKey());
            }
            if (BaseDevice.getDeviceKey().equals(key)) {
                deviceDTO.setButtonKey(BaseDevice.getButtonKey());
            }
        });
        SysUserInfo userInfo = userInfoService.findByName(userName);
        // 根据家庭id查看社区信息
        BaseCommuntity communtity = baseFamilyService.findCommuntityByFamilyId(baseDevice.getFamilyId());
        userInfo.setCommuntityId(communtity.getId());
        userInfo.setFid(baseDevice.getFamilyId());
        String jwtToken = JwtUtil.generateToken(userInfo);
        loginDTO.setToken(jwtToken);
        loginDTO.setUserInfo(userInfo);
        loginDTO.setDevice(deviceDTO);
        return ResponseMessage.ok(loginDTO);
    }


}
