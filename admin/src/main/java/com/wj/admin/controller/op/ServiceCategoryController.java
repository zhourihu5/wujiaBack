package com.wj.admin.controller.op;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.op.OpServiceCategory;
import com.wj.core.service.op.ServiceCategoryService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/category", tags = "服务类别接口模块")
@RestController
@RequestMapping("/v1/category/")
public class ServiceCategoryController {

    private final static Logger logger = LoggerFactory.getLogger(ServiceCategoryController.class);

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @ApiOperation(value = "我的服务类别列表", notes = "我的服务类别列表")
    @GetMapping("allServiceCategory")
    public ResponseMessage<List<OpServiceCategory>> allServiceCategory() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("我的服务类别列表接口:/v1/category/findByFamilyId userId=" + claims.get("userId"));
        List<OpServiceCategory> list = serviceCategoryService.allList();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "添加/更新服务类别", notes = "添加/更新服务类别")
    @PostMapping("addServiceCategory")
    public ResponseMessage addServiceCategory(@RequestBody OpServiceCategory serviceCategory) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("添加/更新服务类别接口:/v1/category/addServiceCategory userId=" + claims.get("userId"));
        serviceCategoryService.saveServiceCategory(serviceCategory);
        return ResponseMessage.ok();
    }

}
