package com.wj.admin.controller.sys;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.dto.DeviceVersionDTO;
import com.wj.core.entity.user.SysVersion;
import com.wj.core.service.user.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(value="/v1/system", tags="系统接口模块")
@RestController
@RequestMapping("/v1")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ApiOperation(value="获取系统版本接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "分页大小")
    })
    @GetMapping("/system/list")
    public ResponseMessage<Page<SysVersion>> getPage(Integer pageNo, Integer pageSize) {
        return ResponseMessage.ok(systemService.getPage(pageNo, pageSize));
    }

    @ApiOperation(value="保存接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", dataType = "SysVersion", value = "升级实体")
    })
    @PostMapping("/system/save")
    public ResponseMessage save(@RequestBody SysVersion version) {
        systemService.save(version);
        return ResponseMessage.ok();
    }

}