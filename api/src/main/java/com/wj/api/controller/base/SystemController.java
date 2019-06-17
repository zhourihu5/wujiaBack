package com.wj.api.controller.base;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.service.user.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="/v1/system", tags="系统接口模块")
@RestController
@RequestMapping("/v1")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ApiOperation(value="获取系统版本接口")
    @RequestMapping("/system/version")
    private ResponseMessage<SysVersion> getVer() {
        return ResponseMessage.ok(systemService.getVer());
    }

}
