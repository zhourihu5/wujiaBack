package com.wj.api.controller.base;


import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseIssue;
import com.wj.core.service.base.BaseIssueService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/issue", tags = "期接口模块")
@RestController
@RequestMapping("/v1/issue/")
public class BaseIssueController {

    private final static Logger logger = LoggerFactory.getLogger(BaseIssueController.class);

    @Autowired
    private BaseIssueService baseIssueService;

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
    @ApiParam(name = "commCode", type = "String", value = "社区Code")
    @GetMapping("findByCommuntity")
    public ResponseMessage<List<BaseIssue>> findByCommuntity(String commCode) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查询社区下所属期 接口:/v1/issue/findByCommuntity userId=" + claims.get("userId"));
        List<BaseIssue> list = baseIssueService.findByCommuntityId(commCode);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "根据ID查询期信息", notes = "根据ID查询期信息")
    @GetMapping("findById")
    public ResponseMessage<BaseIssue> findById(Integer id) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("根据ID查询期信息 接口:/v1/issue/findById userId=" + claims.get("userId"));
        BaseIssue baseIssue = baseIssueService.findById(id);
        return ResponseMessage.ok(baseIssue);
    }



}

