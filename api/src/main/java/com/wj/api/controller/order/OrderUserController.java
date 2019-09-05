package com.wj.api.controller.order;

import com.wj.api.controller.WebSocketServer;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.order.OrderUser;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.message.MessageCommuntityRepository;
import com.wj.core.repository.message.MessageUserRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.service.SendSms;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.message.MessageService;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.order.OrderUserService;
import com.wj.core.service.sendMessage.YunpianSendSms;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.service.user.UserInfoService;
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

import java.io.IOException;
import java.util.List;

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
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private BaseCommuntityService baseCommuntityService;
    @Autowired
    private MessageCommuntityRepository messageCommuntityRepository;
    @Autowired
    private MessageUserRepository messageUserRepository;
    @Autowired
    private UserFamilyService userFamilyService;
    @Autowired
    private SendSms sendSms;
    @Autowired
    private YunpianSendSms yunpianSendSms;

    @ApiOperation(value = "保存配送员订单关系", notes = "保存配送员订单关系")
    @PostMapping("saveOrderUser")
    public ResponseMessage saveOrderUser(@RequestBody OrderUser orderUser) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        Integer count = orderUserService.findCountByOrderId(orderUser.getOrderId());
        if (count > 0) {
            throw new ServiceException("此订单已经有人接单", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        SysUserInfo userInfo = userInfoService.findUserInfo(userId);
        OrderInfo orderInfo = orderService.findOrderDetail(orderUser.getOrderId());
        Message message = new Message();
        message.setTitle("发货通知");
        message.setContent("您好，您购买的商品正在路上，配送员姓名:" + orderInfo.getDeliveryPerson() + "，配送员电话:" + orderInfo.getDeliveryPhone());
        message.setType(4);
        Message newMessage = messageService.saveMessage(message);
        try {
            WebSocketServer.sendInfo(message.getTitle(),userId+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SysUserFamily> userFamilyList = userFamilyService.findByUserId(orderUser.getUserId());
        try {
            userFamilyList.forEach(SysUserFamily -> {
                // 保存消息和用户关系
                messageService.addMessageUser(newMessage.getId(), SysUserFamily.getUserFamily().getUserId(), SysUserFamily.getUserFamily().getFamilyId(), 0);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderService.updateOrderDelivery(userInfo.getNickName(), userInfo.getUserName(), orderUser.getOrderId());
        SysUserInfo user = userInfoService.findUserInfo(orderUser.getUserId());
        yunpianSendSms.sendDelivery(user.getUserName(), orderInfo.getCommodity().getName());
        orderUser.setUserId(userId);
        orderUserService.saveOrderUser(orderUser);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "修改配送员订单关系", notes = "修改配送员订单关系")
    @PostMapping("updateOrderUser")
    public ResponseMessage updateOrderUser(@RequestBody OrderUser orderUser) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        orderUser.setUserId(userId);
        orderUserService.updateOrderUser(orderUser);
        orderService.deliveryOrder(orderUser.getOrderId());
        Integer count = orderService.updateOrdeReceiveDate(orderUser.getOrderId());
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "用户订单分页列表", notes = "用户订单分页列表")
    @GetMapping("/findAll")
    public ResponseMessage<Page<OrderUser>> findAll(String status, String startDate, String endDate, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<OrderUser> page = orderUserService.findAll(userId, status, startDate, endDate, pageable);
        return ResponseMessage.ok(page);
    }


}
