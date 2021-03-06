package com.wj.api.controller.order;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.wx.WxQrCodeService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
public class OrderController {

    public final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private WxQrCodeService wxQrCodeService;

    @ApiOperation(value = "生成小程序二维码", notes = "生成小程序二维码")
    @GetMapping("/v1/order/generateQrCode")
    public ResponseMessage<String> generateQrCodeMini(Integer id) throws Exception {
        String path="images/wxapp/qrcode/orderDetail";
        String fileName="order_"+id+".png";

        String scene=id +"";
        String page="pages/orderDetail/index";

        String result= wxQrCodeService.generateWxappQrCode(path, fileName, scene, page);
        return ResponseMessage.ok(result);

    }

    @ApiOperation(value = "订单详情", notes = "订单详情")
    @GetMapping("/v1/order/findOrderDetail")
    public ResponseMessage<OrderInfo> findOrderDetail(Integer orderId) {
        return ResponseMessage.ok(orderService.findOrderDetail(orderId));
    }

    @ApiOperation(value = "订单分页列表", notes = "订单分页列表")
    @GetMapping("/v1/order/findList")
    public ResponseMessage<Page<OrderInfo>> findList(String status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "create_date");
        Page<OrderInfo> page = orderService.findListByUserId(userId, status, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "BD订单分页列表", notes = "订单分页列表")
    @GetMapping("/order/findListBD")
    public ResponseMessage<Page<OrderInfo>> findListBD(String status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "create_date");
        Page<OrderInfo> page = orderService.findListBD(status, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "下单", notes = "下单")
    @PostMapping("/v1/order/saveOrder")
    public ResponseMessage<OrderInfo> saveOrder(@RequestBody OrderInfo orderInfo) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        orderInfo.setUserId(userId);
        return ResponseMessage.ok(orderService.saveOrder(orderInfo));
    }
    @ApiOperation(value = "删除订单", notes = "删除订单")
    @PostMapping("/v1/order/deleteOrder")
    public ResponseMessage<OrderInfo> deleteOrder(@RequestBody OrderInfo orderInfo) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        orderInfo.setUserId(userId);
        orderService.deleteOrder(orderInfo.getId());
        return ResponseMessage.ok();
    }


    @ApiOperation(value = "支付", notes = "支付")
    @PostMapping("/v1/order/payOrder")
    public ResponseMessage payOrder(@RequestBody OrderInfo orderInfo) {
        orderService.payOrder(orderInfo);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "取消订单", notes = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderInfo", dataType = "OrderInfo", value = "只给order.id就可以", required = true)})
    @PostMapping("/v1/order/cancelOrder")
    public ResponseMessage cancelOrder(@RequestBody OrderInfo orderInfo) {
        orderService.cancelOrder(orderInfo.getId());
        return ResponseMessage.ok();
    }
    @ApiOperation(value = "取消订单", notes = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderInfo", dataType = "OrderInfo", value = "只给order.id就可以", required = true)})
    @PostMapping("/v1/order/cancelOrderPad")
    public ResponseMessage cancelOrderPad(Integer id) {
        orderService.cancelOrder(id);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "确认收获订单", notes = "确认收获订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderInfo", dataType = "OrderInfo", value = "只给order.id就可以", required = true)})
    @PostMapping("/v1/order/receiveOrder")
    public ResponseMessage receiveOrder(@RequestBody OrderInfo orderInfo) {
        orderService.receiveOrder(orderInfo.getId());
        return ResponseMessage.ok();
    }
    @ApiOperation(value = "确认收获订单", notes = "确认收获订单")
    @PostMapping("/v1/order/receiveOrderPad")
    public ResponseMessage receiveOrderPad(Integer id) {
        orderService.receiveOrder(id);
        return ResponseMessage.ok();
    }

}
