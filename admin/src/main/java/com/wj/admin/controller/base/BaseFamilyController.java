package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseFamilyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/family", tags = "家庭接口模块")
@RestController
@RequestMapping("/v1/family/")
public class BaseFamilyController {
    @Autowired
    private BaseFamilyService baseFamilyService;

    @ApiOperation(value = "保存家庭内容", notes = "保存家庭内容")
    @PostMapping("addUnit")
    public ResponseMessage addUnit(@RequestBody BaseFamily family) {
        baseFamilyService.saveFamily(family);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取单元分页信息", notes = "获取单元分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseFamily>> findAll(Integer unitId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseFamily> page = baseFamilyService.findAll(unitId, pageable);
        return ResponseMessage.ok(page);
    }


}

