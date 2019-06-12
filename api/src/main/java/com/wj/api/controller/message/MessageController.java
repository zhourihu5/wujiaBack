package com.wj.api.controller.message;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.dto.MessageReadDTO;
import com.wj.core.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(value="/v1/message", tags="消息接口模块")
@RestController
@RequestMapping("/v1")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value="查询消息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", dataType = "Integer", value = "分页Id"),
            @ApiImplicitParam(name = "type", dataType = "Integer", value = "消息类型0、系统 1、物业 2、社区"),
            @ApiImplicitParam(name = "status", dataType = "Integer", value = "消息状态0、未读 1、已读")
    })
    @GetMapping("/message/list")
    public ResponseMessage<Page<Message>> getList(Integer pageNo, Integer type, Integer status) {
        return ResponseMessage.ok(messageService.getList("", pageNo, type, status));
    }

    @PostMapping("/message/read")
    public ResponseMessage read(@RequestBody MessageReadDTO messageReadDTO) {
        messageService.modifyRead("", messageReadDTO.getId());
        return ResponseMessage.ok();
    }

}
