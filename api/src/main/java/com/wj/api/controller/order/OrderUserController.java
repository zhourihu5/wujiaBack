package com.wj.api.controller.order;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.order.EbizOrderUser;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.order.embeddable.OrderUser;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.message.MessageService;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.order.OrderUserService;
import com.wj.core.service.user.UserInfoService;
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

@Api(value = "/v1/orderUser", tags = "用户订单关系接口模块")
@RestController
@RequestMapping("/v1/orderUser/")
public class OrderUserController {

    public final static Logger logger = LoggerFactory.getLogger(OrderUserController.class);

    @Autowired
    private OrderUserService orderUserService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(value = "保存用户订单关系", notes = "保存用户订单关系")
    @PostMapping("saveOrderUser")
    public ResponseMessage saveOrderUser(@RequestBody OrderUser orderUser) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        SysUserInfo userInfo = userInfoService.findUserInfo(userId);
        Integer count = orderService.updateOrderDelivery(userInfo.getNickName(), userInfo.getUserName(), orderUser.getOrderId());
        OrderInfo orderInfo = orderService.findOrderDetail(orderUser.getOrderId());
        orderUser.setUserId(userId);
        orderUserService.saveOrderUser(orderUser);
        Message message = new Message();
        message.setTitle("发货通知");
        message.setContent("您好，您购买的商品正在路上，配送员姓名:" + orderInfo.getDeliveryPerson() + "，配送员电话:" + orderInfo.getDeliveryPhone());
        message.setType(4);
        messageService.saveMessage(message);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "修改用户订单关系", notes = "保存用户订单关系")
    @PostMapping("updateOrderUser")
    public ResponseMessage updateOrderUser(@RequestBody OrderUser orderUser) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        orderUser.setUserId(userId);
        orderUserService.updateOrderUser(orderUser);
        orderService.deliveryOrder(orderUser.getOrderId());
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "用户订单分页列表", notes = "用户订单分页列表")
    @GetMapping("/findAll")
    public ResponseMessage<Page<EbizOrderUser>> findAll(String status, String startDate, String endDate, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<EbizOrderUser> page = orderUserService.findAll(status, startDate, endDate, pageable);
        return ResponseMessage.ok(page);
    }


}
