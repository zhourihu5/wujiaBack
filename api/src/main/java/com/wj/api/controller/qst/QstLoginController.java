package com.wj.api.controller.qst;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.service.qst.QstLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/qst", tags = "")
@RestController
@RequestMapping("/qst/")
public class QstLoginController {

    public final static Logger logger = LoggerFactory.getLogger(QstLoginController.class);

    @Autowired
    private QstLoginService loginService;

    @ApiOperation(value = "获取访问令牌", notes = "获取访问令牌")
    @GetMapping("accesstoken")
    public ResponseMessage accesstoken() {
        return ResponseMessage.ok(loginService.login());
    }



}
