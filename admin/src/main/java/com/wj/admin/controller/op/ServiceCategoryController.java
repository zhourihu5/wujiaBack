package com.wj.admin.controller.op;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.op.OpServiceCategory;
import com.wj.core.repository.op.ServiceCategoryRepository;
import com.wj.core.service.op.ServeService;
import com.wj.core.service.op.ServiceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/category", tags = "服务类别接口模块")
@RestController
@RequestMapping("/v1/category/")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    /**
     * 我的服务类别列表
     * @return List<OpServiceCategory>
     * @author thz
     */
    @ApiOperation(value = "我的服务类别列表", notes = "我的服务类别列表")
    @GetMapping("findByFamilyId")
    public ResponseMessage<List<OpServiceCategory>> findByFamilyId() {
        List<OpServiceCategory> list = serviceCategoryService.allList();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "添加/更新服务", notes = "添加/更新服务")
    @PostMapping("addServiceCategory")
    public ResponseMessage addServiceCategory(@RequestBody OpServiceCategory serviceCategory) {
        serviceCategoryService.saveServiceCategory(serviceCategory);
        return ResponseMessage.ok();
    }

}
