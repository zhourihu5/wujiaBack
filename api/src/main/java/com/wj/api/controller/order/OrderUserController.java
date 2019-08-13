package com.wj.api.controller.order;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.order.EbizOrderUser;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.order.embeddable.OrderUser;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.order.OrderUserService;
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


    @ApiOperation(value = "保存用户订单关系", notes = "保存用户订单关系")
    @PostMapping("saveOrderUser")
    public ResponseMessage saveOrderUser(@RequestBody OrderUser orderUser) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        orderUser.setUserId(userId);
        orderUser.setStatus("0");
        orderUserService.saveOrderUser(orderUser);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "用户订单分页列表", notes = "用户订单分页列表")
    @GetMapping("/findAll")
    public ResponseMessage<Page<EbizOrderUser>> findAll(String status, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "create_date");
        Page<EbizOrderUser> page = orderUserService.findAll(status, pageable);
        return ResponseMessage.ok(page);
    }


}
