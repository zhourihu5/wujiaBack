package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.CommonUtils;
import com.wj.admin.utils.HttpUtils;
import com.wj.admin.utils.JwtUtil;
import com.wj.admin.utils.ResultUtil;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseDeviceService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.checkerframework.checker.units.qual.min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/user", tags = "用户接口模块")
@RestController
@RequestMapping("/user/")
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

    @ApiOperation(value = "获取用户分页信息", notes = "获取用户分页信息")
    @GetMapping("findUserInfoByName")
    public Object findUserInfoByName(String userName, String nickName, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<SysUserInfo> page = userInfoService.findAll(userName, nickName, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "新增/修改用户", notes = "新增/修改用户")
    @PostMapping("addUser")
    public Object addUser(@RequestBody SysUserInfo user) {
        userInfoService.saveUser(user);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @PostMapping("delUser")
    public Object delUser(@RequestBody SysUserInfo user) {
        userInfoService.delUser(user);
        return ResponseMessage.ok();
    }

}
