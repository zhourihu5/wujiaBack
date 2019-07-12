package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.service.base.BaseDistrictService;
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

@Api(value = "/v1/district", tags = "区接口模块")
@RestController
@RequestMapping("/v1/district/")
public class BaseDistrictController {

    private final static Logger logger = LoggerFactory.getLogger(BaseDistrictController.class);

    @Autowired
    private BaseDistrictService baseDistrictService;

    @ApiOperation(value = "保存区的内容", notes = "保存区的内容")
    @PostMapping("addDistrict")
    public ResponseMessage<BaseDistrict> addDistrict(@RequestBody BaseDistrict district) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存楼内容 接口:/v1/district/addDistrict userId=" + claims.get("userId"));
        return ResponseMessage.ok(baseDistrictService.saveDistrict(district));
    }

    @ApiOperation(value = "获取区分页信息", notes = "获取区分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseDistrict>> findAll(Integer communtityId, Integer issueId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取区分页信息 接口:/v1/district/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseDistrict> page = baseDistrictService.findAll(communtityId, issueId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "查询社区下所属期", notes = "查询社区下所属期")
    @GetMapping("findByCommuntity")
    public ResponseMessage<List<BaseDistrict>> findByCommuntity(Integer communtityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查询社区下所属期 接口:/v1/district/findByCommuntity userId=" + claims.get("userId"));
        List<BaseDistrict> list = baseDistrictService.findByCommuntityId(communtityId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "查询期下所属区", notes = "查询期下所属区")
    @GetMapping("findByIssue")
    public ResponseMessage<List<BaseDistrict>> findByIssue(Integer issueId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查询期下所属区 接口:/v1/district/findByIssue userId=" + claims.get("userId"));
        List<BaseDistrict> list = baseDistrictService.findByIssueId(issueId);
        return ResponseMessage.ok(list);
    }
}

