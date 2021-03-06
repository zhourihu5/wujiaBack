package com.wj.api.controller.base;


import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.base.dto.FamilyBindInfoDTO;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.embeddable.UserFamily;
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


    @ApiOperation(value = "获取每层下的家庭")
    @ApiImplicitParam(name = "storeyCode", dataType = "String", value = "层Code", required = true)
    @GetMapping("getFamilyByStoreyCode")
    public ResponseMessage<List<BaseFamily>> getFamilyByStoreyCode(String storeyCode) {
        List<BaseFamily> baseFamilyList = baseFamilyService.getFamilyByStoreyCode(storeyCode);
        baseFamilyList.forEach(BaseFamily -> {
            BaseFamily.setName(BaseFamily.getNum());
        });
        return ResponseMessage.ok(baseFamilyList);
    }


}

