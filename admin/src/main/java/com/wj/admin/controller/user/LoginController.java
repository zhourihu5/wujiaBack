package com.wj.admin.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.AesUtils;
import com.wj.admin.utils.CommonUtils;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysAuthority;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.AuthorityDTO;
import com.wj.core.service.auth.AuthorityService;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.util.mapper.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    private AuthorityService authorityService;

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
        return ResponseMessage.ok(smsCode);
    }

    /**
     * 登录
     *
     * @param sysUserInfo
     * @return List<UserInfo>
     * @author thz
     */
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("checking")
    public Object checking(@RequestBody SysUserInfo sysUserInfo) {
        String userName = sysUserInfo.getUserName();
        String password = sysUserInfo.getPassword();
        if (userName.isEmpty() || password.isEmpty()) {
            throw new ServiceException("请输入正确的参数", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        AesUtils au = new AesUtils();
        String aesPwd = au.AESEncode(CommonUtils.AESKEY, password);
        SysUserInfo userInfo = userInfoService.findByNameAndPwd(userName, aesPwd);
        if (userInfo == null) {
            throw new ServiceException("账号或密码错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (userInfo.getFlag() != 1) {
            throw new ServiceException("你不是后台用户", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        List<SysAuthority> authList = authorityService.findAll();
        String jwtToken = JwtUtil.generateToken(userInfo);
        List<AuthorityDTO> authorityDTOList = new ArrayList<>();
        for (SysAuthority sysAuthority : authList) {
            AuthorityDTO authorityDTO = new AuthorityDTO();
            String json = "{title:'" + sysAuthority.getTitle() + "',icon:'" + sysAuthority.getIcon() + "'}";
            authorityDTO.setMeta(JSONArray.parse(json));
            authorityDTO.setId(sysAuthority.getId());
            authorityDTO.setComponent(sysAuthority.getComponent());
            authorityDTO.setPath(sysAuthority.getPath());
            authorityDTO.setName(sysAuthority.getName());
            authorityDTO.setRedirect(sysAuthority.getRedirect());
            authorityDTO.setPid(sysAuthority.getPid());
            authorityDTOList.add(authorityDTO);
        }
//        List<AuthorityDTO> a = BeanMapper.mapList(authList, AuthorityDTO.class);
        Map<String, Object> maps = new HashMap<>();
        maps.put("authList", authorityDTOList);
        maps.put("token", jwtToken);
        maps.put("userInfo", userInfo);
        return ResponseMessage.ok(maps);
    }

    /**
     * 获取token
     *
     * @param userName
     * @return String
     * @author thz
     */
    @ApiOperation(value = "获取token", notes = "获取token")
    @GetMapping("getToken")
    public Object getToken(String userName) {
        // Create Jwt token
        SysUserInfo userInfo = userInfoService.findByName(userName);
        String jwtToken = JwtUtil.generateToken(userInfo);
        return ResponseMessage.ok(jwtToken);
    }

}
