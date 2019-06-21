package com.wj.admin.controller.message;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.SysMessageUser;
import com.wj.core.service.message.MessageService;
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

@Api(value="/v1/message", tags="消息接口模块")
@RestController
@RequestMapping("/v1/message/")
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);


    @Autowired
    private MessageService messageService;


    @ApiOperation(value="保存消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Message", dataType = "Message", value = "消息实体"),
    })
    @PostMapping("saveMessage")
    public ResponseMessage saveMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        return ResponseMessage.ok();
    }

    @ApiOperation(value="绑定消息和用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysMessageUser", dataType = "SysMessageUser", value = "消息用户实体"),
    })
    @PostMapping("saveMessageUser")
    public ResponseMessage saveMessageUser(@RequestBody SysMessageUser messageUser) {
        messageService.saveMessageUser(messageUser);
        return ResponseMessage.ok();
    }


    @ApiOperation(value = "消息分页信息", notes = "消息分页信息")
    @GetMapping("findAll")
    public ResponseMessage<Page<Message>> findAll(String title, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        // 获取token
        String token = JwtUtil.getJwtToken();
        // 通过token获取用户信息
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("消息分页信息 接口:/v1/message/findAll userId=" + claims.get("userId"));
        pageNum = pageNum - 1;
        Pageable pageable =  PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<Message> page = messageService.findAll(title, pageable);
        return ResponseMessage.ok(page);
    }
}
