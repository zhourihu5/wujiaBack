package com.wj.api.controller.qst;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.service.qst.QstBindingUserService;
import com.wj.core.service.qst.QstLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/qst/binding", tags = "")
@RestController
@RequestMapping("/qst/binding/")
public class QstBindingUserController {

    public final static Logger logger = LoggerFactory.getLogger(QstBindingUserController.class);

    @Autowired
    private QstBindingUserService bindingUserService;

    @ApiOperation(value = "代理/用户注册", notes = "代理/用户注册")
    @GetMapping("agentregister")
    public ResponseMessage agentregister() {
        return ResponseMessage.ok(bindingUserService.agentregister());
    }



}
