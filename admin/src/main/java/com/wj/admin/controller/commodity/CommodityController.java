package com.wj.admin.controller.commodity;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.service.commodity.CommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/commodity", tags = "商品模块")
@RestController
@RequestMapping("/v1/commodity/")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @ApiOperation(value="保存商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commodity", dataType = "Commodity", value = "商品对象")
    })
    @PostMapping("save")
    public ResponseMessage save(@RequestBody Commodity commodity) {
        Integer userId = JwtUtil.getUserIdFromToken(JwtUtil.getJwtToken());
        commodityService.saveCommodity(commodity, userId);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "主键ID")
    })
    @PostMapping("remove")
    public ResponseMessage remove(Integer id) {
        commodityService.removeCommodity(id);
        return ResponseMessage.ok();
    }


    @ApiOperation(value="商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "name", dataType = "String", value = "商品名称")
    })
    @GetMapping("list")
    public ResponseMessage<Page<Commodity>> list(Integer pageNum, Integer pageSize, String name) {
        return ResponseMessage.ok(commodityService.getCommodityList(name, pageNum, pageSize));
    }


}
