package com.wj.admin.controller.base;


import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.base.dto.FamilyBindInfoDTO;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.user.UserFamilyService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @Autowired
    private UserFamilyService userFamilyService;


//    @ApiOperation(value = "保存家庭内容", notes = "保存家庭内容")
//    @PostMapping("addFamily")
//    public ResponseMessage<BaseFamily> addFamily(@RequestBody BaseFamily family) {
//        String token = JwtUtil.getJwtToken();
//        Claims claims = JwtUtil.parseJwt(token);
//        logger.info("保存家庭内容接口:/v1/family/addFamily userId=" + claims.get("userId"));
//        family = baseFamilyService.saveFamily(family);
//        return ResponseMessage.ok(family);
//    }

    @ApiOperation(value = "获取家庭分页信息", notes = "获取家庭分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<BaseFamily>> findAll(Integer unitId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("获取家庭分页信息 接口:/v1/family/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<BaseFamily> page = baseFamilyService.findAll(unitId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "绑定用户和家庭关系", notes = "绑定用户和家庭关系")
    @PostMapping("addUserAndFamily")
    public ResponseMessage addUserAndFamily(@RequestBody SysUserFamily userFamily) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存家庭内容接口:/v1/family/addUserAndFamily userId=" + claims.get("userId"));
        userFamilyService.addUserAndFamily(userFamily);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "解绑用户和家庭关系", notes = "解绑用户和家庭关系")
    @PostMapping("delUserAndFamily")
    public ResponseMessage delUserAndFamily(@RequestBody UserFamily userFamily) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存家庭内容接口:/v1/family/delUserAndFamily userId=" + claims.get("userId"));
        userFamilyService.delUserAndFamily(userFamily);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取家庭的绑定信息", notes = "获取家庭的绑定信息")
    @ApiImplicitParam(name = "familyId", dataType = "Integer", value = "家庭ID", required = true)
    @GetMapping("bindInfo")
    public ResponseMessage<FamilyBindInfoDTO> bindInfo(Integer familyId) {
        return ResponseMessage.ok(userFamilyService.getFamilyBindInfo(familyId));
    }


}

