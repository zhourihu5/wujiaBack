package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseFloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/floor", tags = "楼接口模块")
@RestController
@RequestMapping("/floor/")
public class BaseFloorController {
    @Autowired
    private BaseFloorService baseFloorService;

    @ApiOperation(value = "保存楼内容", notes = "保存楼内容")
    @PostMapping("addUnit")
    public ResponseMessage addUnit(@RequestBody BaseFloor floor) {
        baseFloorService.saveFloor(floor);
        return ResponseMessage.ok();
    }
}

