package com.wj.api.controller.base;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntityInfo;
import com.wj.core.entity.base.dto.BaseAreaDTO;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.service.base.BaseCommuntityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "/v1/communtityInfo", tags = "社区黄页接口模块")
@RestController
@RequestMapping("/v1/communtityInfo/")
public class CommuntityInfoController {

    @Autowired
    private BaseCommuntityInfoService baseCommuntityInfoService;

    @ApiOperation(value = "社区黄页分页列表", notes = "社区黄页分页列表")
    @GetMapping("findAll")
    public @ResponseBody ResponseMessage<Page<BaseCommuntityInfo>> findAll(Integer communtityId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "start_date");

        Page<BaseCommuntityInfo> list = baseCommuntityInfoService.findAll(communtityId, pageable);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "社区黄页全部列表", notes = "社区黄页全部列表")
    @GetMapping("findList")
    public @ResponseBody ResponseMessage<List<BaseCommuntityInfo>> findList(Integer communtityId) {
        List<BaseCommuntityInfo> list = baseCommuntityInfoService.findList(communtityId);
        return ResponseMessage.ok(list);
    }
}
