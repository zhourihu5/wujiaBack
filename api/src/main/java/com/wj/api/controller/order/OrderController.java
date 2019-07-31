package com.wj.api.controller.order;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.order.OrderService;
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

@Api(value = "/v1/order", tags = "订单接口模块")
@RestController
@RequestMapping("/v1/order/")
public class OrderController {

    public final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "订单详情", notes = "订单详情")
    @GetMapping("findOrderDetail")
    public ResponseMessage<OrderInfo> findOrderDetail(Integer orderId) {
        return ResponseMessage.ok(orderService.findOrderDetail(orderId));
    }

    @ApiOperation(value = "订单分页列表", notes = "订单分页列表")
    @GetMapping("/findList")
    public ResponseMessage<Page<OrderInfo>> findList(String status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
//        String token = JwtUtil.getJwtToken();
//        Claims claims = JwtUtil.parseJwt(token);
//        Integer userId = (Integer) claims.get("userId");
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "create_date");
        Page<OrderInfo> page = orderService.findList(status, pageable);
        return ResponseMessage.ok(page);
    }


    @ApiOperation(value = "下单", notes = "下单")
    @PostMapping("saveOrder")
    public ResponseMessage saveOrder(@RequestBody OrderInfo orderInfo) {
        orderService.saveOrder(orderInfo);
        return ResponseMessage.ok();
    }


}