package com.wj.api.controller.base;


import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.service.base.BaseUnitService;
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

@Api(value = "/v1/unit", tags = "单元接口模块")
@RestController
@RequestMapping("/v1/unit/")
public class BaseUnitController {

    private final static Logger logger = LoggerFactory.getLogger(BaseUnitController.class);

    @Autowired
    private BaseUnitService baseUnitService;


    @ApiOperation(value = "获取单元分页信息", notes = "获取单元分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseUnit>> findAll(Integer floorId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取单元分页信息接口:/v1/unit/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseUnit> page = baseUnitService.findAll(floorId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "查询楼所属单元", notes = "查询楼所属单元")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commCode", dataType = "String", value = "社区Code"),
            @ApiImplicitParam(name = "issuCode", dataType = "String", value = "期Code"),
            @ApiImplicitParam(name = "disCode", dataType = "String", value = " 区Code"),
            @ApiImplicitParam(name = "floorCode", dataType = "String", value = " 楼Code")
    })
    @GetMapping("findByUnit")
    public ResponseMessage<List<BaseUnit>> findByUnit(String commCode, String issuCode, String disCode, String floorCode) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查询楼所属单元接口:/v1/unit/findByUnit userId=" + claims.get("userId"));
        List<BaseUnit> list = baseUnitService.getUnits(commCode, issuCode, disCode, floorCode);
        list.forEach(BaseUnit -> {
            BaseUnit.setName(BaseUnit.getUnitNo());
        });
        return ResponseMessage.ok(list);
    }
}

