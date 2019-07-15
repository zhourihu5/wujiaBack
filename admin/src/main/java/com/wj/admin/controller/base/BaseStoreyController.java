package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseStorey;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.service.base.BaseStoreyService;
import com.wj.core.service.base.BaseUnitService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
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

@Api(value = "/v1/storey", tags = "层接口模块")
@RestController
@RequestMapping("/v1/storey/")
public class BaseStoreyController {

    private final static Logger logger = LoggerFactory.getLogger(BaseStoreyController.class);

    @Autowired
    private BaseStoreyService baseStoreyService;

    @ApiOperation(value = "保存层和住户内容", notes = "保存层和住户内容")
    @PostMapping("addStorey")
    public ResponseMessage addStorey(@RequestBody BaseStorey storey) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存层和住户内容 接口:/v1/storey/addStorey userId=" + claims.get("userId"));
        if (storey.getFamilyCount() == null) throw new ServiceException("每层总户数量不能为空", ErrorCode.INTERNAL_SERVER_ERROR);
        baseStoreyService.saveStoreyAndFamily(storey);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取层分页信息", notes = "获取层分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseStorey>> findAll(Integer unitId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取层分页信息 接口:/v1/storey/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseStorey> page = baseStoreyService.findAll(unitId, pageable);
        return ResponseMessage.ok(page);
    }

}

