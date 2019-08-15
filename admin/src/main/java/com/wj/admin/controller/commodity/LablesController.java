package com.wj.admin.controller.commodity;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.commodity.Lables;
import com.wj.core.service.commodity.CommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/lables", tags = "商品标签模块")
@RestController
@RequestMapping("/v1/lables")
public class LablesController {

    @Autowired
    private CommodityService commodityService;

    @ApiOperation(value="保存标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lables", dataType = "Lables", value = "标签对象")
    })
    @PostMapping("save")
    public ResponseMessage save(@RequestBody Lables lables) {
        commodityService.saveLables(lables);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键Id")
    })
    @GetMapping("remove")
    public ResponseMessage remove(Integer id) {
        commodityService.removeLables(id);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小")
    })
    @GetMapping("list")
    public ResponseMessage<Page<Lables>> save(Integer pageNum, Integer pageSize) {
        Page<Lables> page = commodityService.getLables(pageSize, pageNum);
        return ResponseMessage.ok(page);
    }

}
