package com.wj.admin.controller.shop;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.shop.Shop;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.shop.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/shop", tags = "商家模块")
@RestController
@RequestMapping("/v1/shop/")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @ApiOperation(value="保存商家")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shop", dataType = "Shop", value = "商家")
    })
    @PostMapping("save")
    public ResponseMessage save(@RequestBody Shop shop) {
        shopService.save(shop);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="商家列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "name", dataType = "String", value = "名称")
    })
    @GetMapping("list")
    public ResponseMessage<Page<Shop>> list(Integer pageNum, Integer pageSize, String name) {
        return ResponseMessage.ok(shopService.getList(pageNum, pageSize, name));
    }


}
