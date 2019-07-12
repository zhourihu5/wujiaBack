package com.wj.api.controller.message;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.message.Message;
import com.wj.core.service.message.MessageService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/message", tags = "消息接口模块")
@RestController
@RequestMapping("/v1/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "获取分页信息", notes = "获取分页信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "type", dataType = "Integer", value = "消息类型0、系统 1、物业 2、社区"),
        @ApiImplicitParam(name = "status", dataType = "Integer", value = "消息状态0、未读 1、已读")
    })
    @GetMapping("/findListByUserId")
    public ResponseMessage<Page<Message>> findListByUserId(Integer familyId, Integer status, Integer type, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "createDate");
        Page<Message> page = messageService.findListByUserId(userId, familyId, status, type, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value="获取3条未读消息")
    @GetMapping("/findTopThreeByUserId")
    public ResponseMessage<List<Message>> findTopThreeByUserId(Integer familyId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(messageService.findTopThreeByUserId(userId, familyId, 0));
    }

    @ApiOperation(value="修改是否已读")
    @PostMapping("/updateIsRead")
    public ResponseMessage<Integer> updateIsRead(Integer messageId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(messageService.updateIsRead(messageId, userId, 1));
    }

    @ApiOperation(value="是否有未读消息")
    @GetMapping("/isUnRead")
    public ResponseMessage<Boolean> getIsUnReadMessage() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(messageService.isUnReadMessage(userId, 0));
    }

}
