package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseDeviceService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/device", tags = "设备接口模块")
@RestController
@RequestMapping("/v1/device/")
public class BaseDeviceController {

    private final static Logger logger = LoggerFactory.getLogger(BaseDeviceController.class);

    @Autowired
    private BaseDeviceService baseDeviceService;

    @ApiOperation(value = "获取设备分页信息", notes = "获取设备分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseDevice>> findAll(Integer flag, Integer status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取设备分页信息 接口:/v1/device/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseDevice> page = baseDeviceService.findAll(flag, status, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "保存设备内容", notes = "保存设备内容")
    @PostMapping("saveDevice")
    public ResponseMessage saveDevice(@RequestBody BaseDevice device) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存设备内容 接口:/v1/device/saveDevice userId=" + claims.get("userId"));
        device.setStatus(1);
        baseDeviceService.saveDevice(device);
        return ResponseMessage.ok();
    }

}

