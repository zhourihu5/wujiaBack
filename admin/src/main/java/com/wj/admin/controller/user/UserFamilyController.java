package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.admin.utils.ResultUtil;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "/v1/userFamily", tags = "用户家庭接口模块")
@RestController
@RequestMapping("/v1/userFamily/")
public class UserFamilyController {

    private final static Logger logger = LoggerFactory.getLogger(UserFamilyController.class);

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
    public ResponseMessage<List<SysUserFamily>> findByUserId(Integer userId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("根据用户id查看用户家庭信息接口:/v1/userFamily/findByUserId userId=", claims.get("userId"));
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
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("家庭成员列表接口:/v1/userFamily/findFamilyUserList userId=", claims.get("userId"));
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
    @GetMapping("addFamily")
    public ResponseMessage addFamily(String userName, int familyId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("首页添加家庭成员接口:/v1/userFamily/addFamily userId=" + claims.get("userId"));
        Integer code = userInfoService.saveUserAndFamily(userName, familyId);
        if (code.equals(1)) {
            return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "手机号已存在");
        }
        return ResponseMessage.ok();
    }

}
