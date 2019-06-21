package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.message.SysMessageUser;
import com.wj.core.entity.user.SysScreen;
import com.wj.core.service.user.ScreenService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/screen", tags = "屏保接口模块")
@RestController
@RequestMapping("/v1/screen/")
public class ScreenController {

    private final static Logger logger = LoggerFactory.getLogger(ScreenController.class);

    @Autowired
    private ScreenService screenService;

    @ApiOperation(value = "查看屏保信息", notes = "查看屏保信息")
    @GetMapping("findAll")
    public ResponseMessage<List<SysScreen>> findAll() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查看屏保信息接口:/v1/screen/findAll userId=" + claims.get("userId"));
        List<SysScreen> list = screenService.findAll();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "保存和修改屏保信息", notes = "保存和修改屏保信息")
    @PostMapping("saveScreen")
    public ResponseMessage saveScreen(@RequestBody SysScreen sysScreen) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("保存和修改屏保信息 接口:/v1/screen/saveScreen userId=" + claims.get("userId"));
        screenService.saveScreen(sysScreen);
        return ResponseMessage.ok();
    }


}
