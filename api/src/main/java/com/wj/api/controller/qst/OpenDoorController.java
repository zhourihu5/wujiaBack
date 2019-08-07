package com.wj.api.controller.qst;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.service.qst.OpenDoorService;
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
public class OpenDoorController {

    public final static Logger logger = LoggerFactory.getLogger(OpenDoorController.class);

    @Autowired
    private OpenDoorService openDoorService;

    @ApiOperation(value = "远程开锁", notes = "远程开锁")
    @GetMapping("OpenDoor")
    public ResponseMessage OpenDoor() {
        return ResponseMessage.ok(openDoorService.OpenDoor());
    }



}
