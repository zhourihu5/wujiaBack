package com.wj.api.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
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
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.message.MessageService;
import com.wj.core.service.sendMessage.YunpianSendSms;
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
import java.util.*;

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
    private YunpianSendSms yunpianSendSms;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private ApplyLockService applyLockService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private BaseFamilyService baseFamilyService;


    @ApiOperation(value = "绑定用户信息", notes = "绑定用户信息")
    @PostMapping("bindingUser")
    public ResponseMessage<XcxLoginDTO> bindingUser(@RequestBody BindingDTO bindingDTO) {
        Object data = redisHelper.getValue(bindingDTO.getUserName());
//        // TODO 测试期间0000放行
//        if (!bindingDTO.getSmsCode().equals("0000") && StringUtils.isNotBlank(bindingDTO.getSmsCode())) {
//            if (!String.valueOf(data).equals(bindingDTO.getSmsCode())) {
//                throw new ServiceException("验证码不正确", ErrorCode.INTERNAL_SERVER_ERROR);
//            }
//        }
        if (!String.valueOf(data).equals(bindingDTO.getSmsCode())) {
            throw new ServiceException("验证码不正确", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        XcxLoginDTO loginDTO = new XcxLoginDTO();
        SysUserInfo userInfo = userInfoService.findByName(bindingDTO.getUserName());
        if (userInfo == null) {
            SysUserInfo sysUserInfo = new SysUserInfo();
            sysUserInfo.setUserName(bindingDTO.getUserName());
            sysUserInfo.setIcon(bindingDTO.getCover());
            sysUserInfo.setWxCover(bindingDTO.getCover());
            sysUserInfo.setNickName(bindingDTO.getNickName());
            sysUserInfo.setWxNickName(bindingDTO.getNickName());
            sysUserInfo.setWxOpenId(bindingDTO.getOpenid());
            sysUserInfo.setFlag(4);
            sysUserInfo.setCreateDate(new Date());
            sysUserInfo = userInfoService.addUser(sysUserInfo);
            loginDTO.setUserInfo(sysUserInfo);
            String jwtToken = JwtUtil.generateToken(sysUserInfo);
            loginDTO.setToken(jwtToken);
            loginDTO.setUserInfo(sysUserInfo);
            loginDTO.setIsBindingFamily("0");
            loginDTO.setUnRead(messageService.isUnReadMessage(sysUserInfo.getId(), 0));
        } else {
            bindingService.bindingUser(bindingDTO.getUserName(), bindingDTO.getCover(), bindingDTO.getNickName(), bindingDTO.getOpenid());
            String jwtToken = JwtUtil.generateToken(userInfo);
            loginDTO.setToken(jwtToken);
            loginDTO.setUserInfo(userInfo);
            List<SysUserFamily> userFamilyList = userFamilyService.findByUserId(userInfo.getId());
            if (userFamilyList.size() <= 0) {
                loginDTO.setIsBindingFamily("0");
                List<ApplyLock> applyLockList = applyLockService.findByUserId(userInfo.getId());
                if (applyLockList.size() > 0) {
                    loginDTO.setApplyLock(applyLockList.get(0));
//                    loginDTO.setIsApplyLock(applyLockList.get(0).getStatus());
                }
                return ResponseMessage.ok(loginDTO);
            }
            List<BaseFamily> familyList = new ArrayList<>();
            for (SysUserFamily sysUserFamily: userFamilyList) {
                BaseFamily baseFamily = new BaseFamily();
                baseFamily.setId(sysUserFamily.getUserFamily().getFamilyId());
                BaseCommuntity baseCommuntity = baseFamilyService.findCommuntityByFamilyId1(sysUserFamily.getUserFamily().getFamilyId());
                baseFamily.setName(baseCommuntity.getName());
                baseFamily.setCommuntity(baseCommuntity);
                familyList.add(baseFamily);
            }
            loginDTO.setFamilyList(familyList);
            List<BaseCommuntity> communtityList = addressService.findByUserId(userInfo.getId());
            if (communtityList.size() > 0) {
                loginDTO.setCommuntityName(communtityList.get(0).getName());
                loginDTO.setCommuntityList(communtityList);
                // 根据所在社区选择活动
                List<Activity> activityList = activityService.findList(userInfo.getId(), communtityList.get(0).getId());
                loginDTO.setActivityList(activityList);
            }
            loginDTO.setIsBindingFamily("1");
            loginDTO.setUnRead(messageService.isUnReadMessage(userInfo.getId(), 0));
        }
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
    public ResponseMessage<XcxLoginDTO> checkBinding(String code) {
        Object object = wxLoginService.wxLogin(code);
        JSONObject json = JSON.parseObject(object.toString());
        String openid = json.getString("openid");
        String session_key = json.getString("session_key");
        XcxLoginDTO loginDTO = new XcxLoginDTO();
        if (openid != null) {
            SysUserInfo userInfo = bindingService.findByOpenId(openid);
            if (userInfo != null) {
                String jwtToken = JwtUtil.generateToken(userInfo);
                loginDTO.setToken(jwtToken);
                loginDTO.setUserInfo(userInfo);
                List<SysUserFamily> userFamilyList = userFamilyService.findByUserId(userInfo.getId());
                if (userFamilyList.size() <= 0) {
                    loginDTO.setIsBindingFamily("0");
                    List<ApplyLock> applyLockList = applyLockService.findByUserId(userInfo.getId());
                    if (applyLockList.size() > 0) {
                        loginDTO.setApplyLock(applyLockList.get(0));
                    }
                    return ResponseMessage.ok(loginDTO);
                }
//                else {
//                    BaseFamily baseFamily = new BaseFamily();
//                    baseFamily.setId(userFamilyList.get(i));
//                    //TODO 用户存在两个家庭的处理方式，现在默认取一个
//                    userInfo.setFid(userFamilyList.get(0).getUserFamily().getFamilyId());
//                }
                List<BaseFamily> familyList = new ArrayList<>();
                for (SysUserFamily sysUserFamily: userFamilyList) {
                    BaseFamily baseFamily = new BaseFamily();
                    baseFamily.setId(sysUserFamily.getUserFamily().getFamilyId());
                    BaseCommuntity baseCommuntity = baseFamilyService.findCommuntityByFamilyId1(sysUserFamily.getUserFamily().getFamilyId());
                    baseFamily.setName(baseCommuntity.getName());
                    baseFamily.setCommuntity(baseCommuntity);
                    familyList.add(baseFamily);
                }
                loginDTO.setFamilyList(familyList);

                List<BaseCommuntity> communtityList = addressService.findByUserId(userInfo.getId());
                if (communtityList.size() > 0) {
                    loginDTO.setCommuntityName(communtityList.get(0).getName());
                    loginDTO.setCommuntityList(communtityList);
                    // 根据所在社区选择活动
                    List<Activity> activityList = activityService.findList(userInfo.getId(), communtityList.get(0).getId());
                    loginDTO.setActivityList(activityList);
                }
                loginDTO.setIsBindingFamily("1");
                loginDTO.setUnRead(messageService.isUnReadMessage(userInfo.getId(), 0));
            }
            loginDTO.setOpenid(openid);
        }
        return ResponseMessage.ok(loginDTO);
    }

    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @GetMapping("sendMsg")
    public ResponseMessage sendMsg(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        Object code = redisHelper.getValue(userName);
        String smsCode = String.valueOf(code);
        try {
            if (code == null) {
                smsCode = CommonUtils.createRandomNum(6);// 生成随机数
                redisHelper.valuePut(userName, smsCode);
            }
            // 发送验证码
            Integer result = yunpianSendSms.sendMessage(userName, smsCode);
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
            if (result != 0) {
                throw new ServiceException("发送失败", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            return ResponseMessage.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.ok();
    }

}
