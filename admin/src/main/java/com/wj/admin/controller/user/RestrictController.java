package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.user.SysRestrict;
import com.wj.core.entity.user.SysRole;
import com.wj.core.entity.user.SysScreen;
import com.wj.core.service.user.RestrictService;
import com.wj.core.service.user.ScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/restrict", tags = "限行接口模块")
@RestController
@RequestMapping("/v1/restrict/")
public class RestrictController {

    @Autowired
    private RestrictService restrictService;

    @ApiOperation(value = "新增/修改限行", notes = "新增/修改限行")
    @PostMapping("addRestrict")
    public ResponseMessage addRestrict(@RequestBody SysRestrict restrict) {
        restrictService.saveRestrict(restrict);
        return ResponseMessage.ok();
    }

}
