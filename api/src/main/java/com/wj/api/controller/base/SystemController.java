package com.wj.api.controller.base;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.base.dto.DeviceVersionDTO;
import com.wj.core.entity.user.SysVersion;
import com.wj.core.service.user.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value="/system", tags="系统接口模块")
@RestController
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ApiOperation(value="获取系统版本接口")
    @GetMapping("/v1/system/version")
    private ResponseMessage<SysVersion> getVer() {
        return ResponseMessage.ok(systemService.getVer());
    }

    @PostMapping("/system/updateVer")
    public ResponseMessage updateVer(@ModelAttribute DeviceVersionDTO d) {
        systemService.updateVer(d);
        return ResponseMessage.ok();
    }
}
