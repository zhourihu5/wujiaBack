package com.wj.api.controller.delivery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.BindingDTO;
import com.wj.core.entity.user.dto.XcxLoginDTO;
import com.wj.core.helper.impl.RedisHelperImpl;
import com.wj.core.service.SendSms;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.address.AddressService;
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.sendMessage.YunpianSendSms;
import com.wj.core.service.user.BindingService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.service.wx.WxLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Api(value = "/wx/delivery", tags = "微信送货端用户登录接口模块")
@RestController
@RequestMapping("/wx/delivery/")
public class DeliveryLoginController {

    public final static Logger logger = LoggerFactory.getLogger(DeliveryLoginController.class);

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

    @Autowired
    private OrderService orderService;

    @Autowired
    private YunpianSendSms yunpianSendSms;


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
            throw new ServiceException("请联系吾家管理员", ErrorCode.INTERNAL_SERVER_ERROR);
        } else {
            bindingService.deliverybindingUser(bindingDTO.getUserName(), bindingDTO.getCover(), bindingDTO.getNickName(), bindingDTO.getOpenid());
            String jwtToken = JwtUtil.generateToken(userInfo);
            loginDTO.setToken(jwtToken);
            loginDTO.setUserInfo(userInfo);
            //选单列表
            Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "create_date");
            Page<OrderInfo> page = orderService.findList("2", pageable);
            loginDTO.setList(page.getContent());
        }
        return ResponseMessage.ok(loginDTO);
    }


    @ApiOperation(value = "检查配送端用户是否存在", notes = "检查配送端用户是否存在")
    @GetMapping("checkBinding")
    public ResponseMessage<XcxLoginDTO> checkBinding(String code) {
        Object object = wxLoginService.wxBdLogin(code);
        JSONObject json = JSON.parseObject(object.toString());
        String openid = json.getString("openid");
        XcxLoginDTO loginDTO = new XcxLoginDTO();
        if (openid != null) {
            SysUserInfo userInfo = bindingService.findByOpenId(openid);
            if (userInfo != null) {
                String jwtToken = JwtUtil.generateToken(userInfo);
                loginDTO.setToken(jwtToken);
                loginDTO.setUserInfo(userInfo);
                //选单列表
                Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "create_date");
                Page<OrderInfo> page = orderService.findList("2", pageable);
                loginDTO.setList(page.getContent());
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
            }, 20 * 60 * 1000);
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
