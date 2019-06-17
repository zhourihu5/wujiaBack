package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.service.base.BaseCommuntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/communtity", tags = "社区接口模块")
@RestController
@RequestMapping("/communtity/")
public class BaseCommuntityController {
    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @ApiOperation(value = "根据ID查询社区信息", notes = "根据ID查询社区信息")
    @GetMapping("findById")
    public ResponseMessage<BaseCommuntity> findById(Integer id) {
        BaseCommuntity baseCommuntity = baseCommuntityService.findById(id);
        return ResponseMessage.ok(baseCommuntity);
    }

    @ApiOperation(value = "保存社区内容", notes = "保存社区内容")
    @GetMapping("addCommuntity")
    public ResponseMessage addCommuntity(BaseCommuntity communtity) {
        baseCommuntityService.saveCommuntity(communtity);
        return ResponseMessage.ok();
    }
}

