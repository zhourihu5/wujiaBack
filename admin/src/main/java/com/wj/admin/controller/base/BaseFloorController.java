package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseFloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/floor", tags = "楼接口模块")
@RestController
@RequestMapping("/v1/floor/")
public class BaseFloorController {
    @Autowired
    private BaseFloorService baseFloorService;

    @ApiOperation(value = "保存楼内容", notes = "保存楼内容")
    @PostMapping("addUnit")
    public ResponseMessage addUnit(@RequestBody BaseFloor floor) {
        baseFloorService.saveFloor(floor);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取楼分页信息", notes = "获取楼分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseFloor>> findAll(Integer communtityId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseFloor> page = baseFloorService.findAll(communtityId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "查询社区下所属楼", notes = "查询社区下所属楼")
    @GetMapping("findByFloor")
    public ResponseMessage<List<BaseFloor>> findByFloor(Integer communtityId) {
        List<BaseFloor> list = baseFloorService.findByCommuntityId(communtityId);
        return ResponseMessage.ok(list);
    }

}

