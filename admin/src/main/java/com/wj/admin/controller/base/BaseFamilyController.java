package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseFamilyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/family", tags = "家庭接口模块")
@RestController
@RequestMapping("/family/")
public class BaseFamilyController {
    @Autowired
    private BaseFamilyService baseFamilyService;

    @ApiOperation(value = "保存家庭内容", notes = "保存家庭内容")
    @PostMapping("addUnit")
    public ResponseMessage addUnit(@RequestBody BaseFamily family) {
        baseFamilyService.saveFamily(family);
        return ResponseMessage.ok();
    }
}

