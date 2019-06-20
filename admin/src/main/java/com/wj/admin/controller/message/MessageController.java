package com.wj.admin.controller.message;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.message.dto.MessageDTO;
import com.wj.core.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="/v1/message", tags="消息接口模块")
@RestController
@RequestMapping("/v1")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @ApiOperation(value="保存消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "messageDTO", dataType = "MessageDTO", value = "消息实体"),
    })
    @PostMapping("/message/save")
    public ResponseMessage saveMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseMessage.ok();
    }

}
