package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/v1/unit", tags = "单元接口模块")
@RestController
@RequestMapping("/unit/")
public class BaseUnitController {
    @Autowired
    private BaseUnitService baseUnitService;

    @ApiOperation(value = "保存单元内容", notes = "保存单元内容")
    @GetMapping("addUnit")
    public ResponseMessage addUnit(BaseUnit unit) {
        baseUnitService.saveUnit(unit);
        return ResponseMessage.ok();
    }
}

