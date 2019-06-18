package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseFamilyService;
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

@Api(value = "/v1/family", tags = "家庭接口模块")
@RestController
@RequestMapping("/v1/family/")
public class BaseFamilyController {

    private final static Logger logger = LoggerFactory.getLogger(BaseFamilyController.class);

    @Autowired
    private BaseFamilyService baseFamilyService;

    @ApiOperation(value = "保存家庭内容", notes = "保存家庭内容")
    @PostMapping("addUnit")
    public ResponseMessage addUnit(@RequestBody BaseFamily family) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存家庭内容接口:/v1/family/addUnit userId=", claims.get("userId"));
        baseFamilyService.saveFamily(family);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取单元分页信息", notes = "获取单元分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseFamily>> findAll(Integer unitId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取单元分页信息接口:/v1/family/findAll userId=", claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseFamily> page = baseFamilyService.findAll(unitId, pageable);
        return ResponseMessage.ok(page);
    }


}

