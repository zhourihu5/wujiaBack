package com.wj.api.controller.base;


import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseStorey;
import com.wj.core.service.base.BaseStoreyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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


    @ApiOperation(value = "查询楼所属单元的每层", notes = "查询楼所属单元的每层")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commCode", dataType = "String", value = "社区Code"),
            @ApiImplicitParam(name = "issuCode", dataType = "String", value = "期Code"),
            @ApiImplicitParam(name = "disCode", dataType = "String", value = " 区Code"),
            @ApiImplicitParam(name = "floorCode", dataType = "String", value = "楼Code"),
            @ApiImplicitParam(name = "unitCode", dataType = "String", value = "单元Code")
    })
    @GetMapping("list")
    public ResponseMessage<List<BaseStorey>> getStoreys(String commCode, String issuCode, String disCode, String floorCode, String unitCode) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查询楼所属楼层接口:/v1/unit/findByUnit userId=" + claims.get("userId"));
        List<BaseStorey> list = baseStoreyService.getStoreys(commCode, issuCode, disCode, floorCode, unitCode);
        return ResponseMessage.ok(list);
    }

}

