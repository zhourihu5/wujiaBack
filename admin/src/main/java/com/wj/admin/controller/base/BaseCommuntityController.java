package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.dto.BaseCommuntityDTO;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.BaseCommuntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/communtity", tags = "社区接口模块")
@RestController
@RequestMapping("/v1/communtity/")
public class BaseCommuntityController {
    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @ApiOperation(value = "获取社区分页信息", notes = "获取社区分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseCommuntity>> findAll(Integer areaCode, String name, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseCommuntity> page = baseCommuntityService.findAll(areaCode, name, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "根据ID查询社区信息", notes = "根据ID查询社区信息")
    @GetMapping("findById")
    public ResponseMessage<BaseCommuntity> findById(Integer id) {
        BaseCommuntity baseCommuntity = baseCommuntityService.findById(id);
        return ResponseMessage.ok(baseCommuntity);
    }

    @ApiOperation(value = "保存社区内容", notes = "保存社区内容")
    @PostMapping("addCommuntity")
    public ResponseMessage addCommuntity(@RequestBody BaseCommuntity communtity) {
        baseCommuntityService.saveCommuntity(communtity);
        return ResponseMessage.ok();
    }
}

