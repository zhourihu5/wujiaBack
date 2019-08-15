package com.wj.admin.controller.order;


import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.PadModule;
import com.wj.core.entity.card.dto.CreateCardDTO;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.service.card.CardService;
import com.wj.core.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(value="/v1/order", tags="订单接口模块")
@RestController
@RequestMapping("/v1")
public class OrderController {


    @Autowired
    private OrderService orderService;



    @ApiOperation(value="订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer", value = "页号"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "大小"),
            @ApiImplicitParam(name = "startDate", dataType = "Date", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", dataType = "Date", value = "结束时间"),
            @ApiImplicitParam(name = "status", dataType = "String", value = "订单状态 1.待付款 2.待收货 3.已收货 4.已过期"),
            @ApiImplicitParam(name = "activityName", dataType = "String", value = "活动名称")
    })
    @GetMapping("/order/list")
    public ResponseMessage<Page<OrderInfo>> list(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String activityName) {
        return ResponseMessage.ok(orderService.getList(pageNum, pageSize, startDate, endDate, status, activityName));
    }

}
