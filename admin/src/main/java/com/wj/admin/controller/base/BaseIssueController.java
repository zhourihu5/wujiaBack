package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseIssue;
import com.wj.core.service.base.BaseFloorService;
import com.wj.core.service.base.BaseIssueService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "/v1/issue", tags = "期接口模块")
@RestController
@RequestMapping("/v1/issue/")
public class BaseIssueController {

    private final static Logger logger = LoggerFactory.getLogger(BaseIssueController.class);

    @Autowired
    private BaseIssueService baseIssueService;

    @ApiOperation(value = "保存楼期内容", notes = "保存楼期内容")
    @PostMapping("addIssue")
    public ResponseMessage addIssue(@RequestBody BaseIssue issue) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存楼内容 接口:/v1/issue/addIssue userId=" + claims.get("userId"));
        baseIssueService.saveIssue(issue);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取期分页信息", notes = "获取期分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseIssue>> findAll(Integer communtityId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取楼分页信息 接口:/v1/issue/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseIssue> page = baseIssueService.findAll(communtityId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "查询社区下所属期", notes = "查询社区下所属期")
    @GetMapping("findByCommuntity")
    public ResponseMessage<List<BaseIssue>> findByCommuntity(Integer communtityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查询社区下所属期 接口:/v1/issue/findByCommuntity userId=" + claims.get("userId"));
        List<BaseIssue> list = baseIssueService.findByCommuntityId(communtityId);
        return ResponseMessage.ok(list);
    }



}

