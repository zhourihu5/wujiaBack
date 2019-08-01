package com.wj.api.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.HttpUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.api.utils.ResultUtil;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.IndexDTO;
import com.wj.core.entity.user.dto.UserInfoDTO;
import com.wj.core.service.base.*;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/user", tags = "用户接口模块")
@RestController
@RequestMapping("/v1/user/")
public class UserInfoController {

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private BaseDeviceService baseDeviceService;

    @Autowired
    private BaseFamilyService baseFamilyService;

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 获取用户信息(PAD端首页接口)
     * @param
     * @return
     * @author
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("findUserInfo")
    public ResponseMessage<IndexDTO> findUserInfo(HttpServletRequest request) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer)claims.get("userId");
        // 根据机器key获取家庭ID
        String key = request.getParameter("deviceKey");
        // 设备信息-对应家庭
        BaseDevice baseDevice = baseDeviceService.findByKey(key);
        if (baseDevice == null) {
            throw new ServiceException("此设备没有绑定家庭", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        // 家庭成员列表 根据机器key查询家庭ID 根据家庭ID查询家庭成员
        List<SysUserInfo> sysUserInfoList = userFamilyService.findFamilyToUser(baseDevice.getFamilyId());
        // 根据家庭id查看社区信息
        BaseCommuntity communtity = baseFamilyService.findCommuntityByFamilyId(baseDevice.getFamilyId());
        IndexDTO indexDTO = new IndexDTO();
        indexDTO.setUserInfoList(sysUserInfoList);
        indexDTO.setCommuntity(communtity);
        return ResponseMessage.ok(indexDTO);
    }

    @ApiOperation(value = "小程序更新用户信息", notes = "小程序更新用户信息")
    @PostMapping("updateInfo")
    public ResponseMessage updateInfo(SysUserInfo userInfo) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer)claims.get("userId");
        userInfoService.updateInfo(userInfo);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    @PostMapping("findUserInfo")
    public ResponseMessage<SysUserInfo> findUserInfo() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);

        Integer userId = (Integer)claims.get("userId");
        return ResponseMessage.ok(userInfoService.findUserInfo(userId));
    }


}
