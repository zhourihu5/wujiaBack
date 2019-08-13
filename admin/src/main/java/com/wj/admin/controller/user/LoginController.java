package com.wj.admin.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.AesUtils;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.user.SysAuthority;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.AuthorityDTO;
import com.wj.core.entity.user.dto.LoginDTO;
import com.wj.core.service.auth.AuthorityService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.QstCommuntityService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(value = "/login", tags = "用户接口模块")
@RestController
@RequestMapping("/login/")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private QstCommuntityService qstCommuntityService;

    /**
     * 登录
     *
     * @param sysUserInfo
     * @return List<UserInfo>
     * @author thz
     */
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("checking")
    public ResponseMessage checking(@RequestBody SysUserInfo sysUserInfo) {
        String userName = sysUserInfo.getUserName();
        String password = sysUserInfo.getPassword();
        logger.info("登录接口:/login/checking userName=" + userName);
        if (userName.isEmpty() || password.isEmpty()) {
            throw new ServiceException("请输入正确的参数", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        AesUtils au = new AesUtils();
        String aesPwd = au.AESEncode(CommonUtils.AESKEY, password);
        logger.info("AES密码aesPwd=" + aesPwd);
        SysUserInfo userInfo = userInfoService.findByNameAndPwd(userName, aesPwd);
        if (userInfo == null) {
            throw new ServiceException("账号或密码错误", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (userInfo.getFlag() != 1) {
            throw new ServiceException("你不是后台用户", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        // 测试代码
        List<SysAuthority> authList = authorityService.findAll();
        // 正式代码
//        List<SysAuthority> authList = authorityService.getAuthByUserId(userInfo.getId());
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
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setAuthorityList(authorityDTOList);
        loginDTO.setToken(jwtToken);
        loginDTO.setUserInfo(userInfo);
        return ResponseMessage.ok(loginDTO);
    }


}
