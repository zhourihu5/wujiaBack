package com.wj.api.controller.user;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.ResultUtil;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "/v1/userFamily", tags = "用户家庭接口模块")
@RestController
@RequestMapping("/v1/userFamily/")
public class UserFamilyController {

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据用户id查看用户家庭信息
     * @param userId
     * @return ResponseMessage<List<SysUserFamily>>
     * @author thz
     */
    @ApiOperation(value = "根据用户id查看用户家庭信息", notes = "根据用户id查看用户家庭信息")
    @GetMapping("findByUserId")
    public ResponseMessage<List<SysUserFamily>> findByUserId(int userId) {
        List<SysUserFamily> list = userFamilyService.findByUserId(userId);
        return ResponseMessage.ok(list);
    }

    /**
     * 家庭成员列表
     * @param familyId
     * @return ResponseMessage<List<SysUserInfo>>
     * @author thz
     */
    @ApiOperation(value = "家庭成员列表", notes = "家庭成员列表")
    @GetMapping("findFamilyUserList")
    public ResponseMessage<List<SysUserInfo>> findFamilyUserList(Integer familyId) {
        // 家庭列表 根据机器key查询家庭ID 根据家庭ID查询家庭成员
        List<SysUserInfo> sysUserInfoList = userFamilyService.findFamilyToUser(familyId);
        return ResponseMessage.ok(sysUserInfoList);
    }

    /**
     * 首页添加家庭成员
     * @param userName
     * @param familyId
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "首页添加家庭成员", notes = "首页添加家庭成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", dataType = "String", value = "用户名称", required = true),
            @ApiImplicitParam(name = "familyId", dataType = "Integer", value = "家庭ID", required = true)})
    @PostMapping("addFamily")
    public ResponseMessage addFamily(String userName, Integer familyId) {
        Integer code = userInfoService.saveUserAndFamily(userName, familyId);
        if (code.equals(1)) {
            return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "手机号已存在");
        }
        return ResponseMessage.ok();
    }

}
