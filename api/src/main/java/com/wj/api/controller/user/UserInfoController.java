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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/user", tags = "用户接口模块")
@RestController
@RequestMapping("/v1/user/")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private BaseDeviceService baseDeviceService;

    @Autowired
    private BaseFamilyService baseFamilyService;

    @Autowired
    private FamilyCommuntityService familyCommuntityService;

    /**
     * 获取用户信息(PAD端首页接口)
     * @param
     * @return
     * @author
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("findUserInfo")
    public ResponseMessage<IndexDTO> findUserInfo(String key) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer)claims.get("userId");
        // 个人信息
        SysUserInfo userInfo = userInfoService.findUserInfo(userId);
        // 获取家庭ID
        BaseDevice baseDevice = baseDeviceService.findByKey(key);
        // 家庭成员列表 根据机器key查询家庭ID 根据家庭ID查询家庭成员
        List<SysUserInfo> sysUserInfoList = userFamilyService.findFamilyToUser(baseDevice.getFamilyId());
        // 根据家庭id查看社区信息-社区ID
        Integer communtityId = familyCommuntityService.findByFamilyId(baseDevice.getFamilyId());
        // 查询当前社区信息
        BaseCommuntity baseCommuntity = baseCommuntityService.findById(communtityId);
        if (null == baseCommuntity) {
            return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "数据异常");
        }
        BaseArea baseArea = baseAreaService.findById(baseCommuntity.getCity());
        if (null == baseArea) {
            return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "数据异常");
        }
        List<BaseDevice> baseDeviceList = baseDeviceService.findByFamilyId(baseDevice.getFamilyId());
        // 天气
        String json = null;
        try {
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + CommonUtils.APPCODE);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("area", baseArea.getAreaName());
            HttpResponse response = HttpUtils.doGet(CommonUtils.HOST, CommonUtils.PATH, CommonUtils.METHOD, headers, querys);
            json = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        IndexDTO indexDTO = new IndexDTO();
        indexDTO.setSysUserInfo(userInfo);
        indexDTO.setSysUserInfoList(sysUserInfoList);
        indexDTO.setWeather(JSONArray.parse(json));
        indexDTO.setCommuntity(baseCommuntity);
        indexDTO.setBaseDeviceList(baseDeviceList);
        return ResponseMessage.ok(indexDTO);
    }

}
