package com.wj.api.controller.base;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseCommuntityInfo;
import com.wj.core.service.base.BaseCommuntityInfoService;
import com.wj.core.service.base.BaseCommuntityService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value = "/v1/communtityInfo", tags = "d接口模块")
@RestController
@RequestMapping("/v1/communtityInfo/")
public class CommuntityController {

    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @ApiOperation(value = "获取社区分页信息", notes = "获取社区分页信息")
    @GetMapping("findByCityCodeAndName")
    public ResponseMessage<Page<BaseCommuntity>> findByCityCodeAndName(Integer cityCode, String name, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseCommuntity> page = baseCommuntityService.findByCityCodeAndName(cityCode, name, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "获取社区分页信息", notes = "获取社区分页信息")
    @GetMapping("findByCode")
    public ResponseMessage<Page<BaseCommuntity>> findByCode(Integer cityCode, String name, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseCommuntity> page = baseCommuntityService.findByCityCodeAndName(cityCode, name, pageable);
        return ResponseMessage.ok(page);
    }

}
