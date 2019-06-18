package com.wj.admin.controller.user;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.user.SysScreen;
import com.wj.core.service.user.ScreenService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/v1/screen", tags = "屏保接口模块")
@RestController
@RequestMapping("/v1/screen/")
public class ScreenController {

    private final static Logger logger = LoggerFactory.getLogger(ScreenController.class);

    @Autowired
    private ScreenService screenService;

    /**
     * 查看屏保信息
     *
     * @param
     * @return SysScreen
     * @author thz
     */
    @ApiOperation(value = "查看屏保信息", notes = "查看屏保信息")
    @GetMapping("findAll")
    public ResponseMessage<SysScreen> findAll() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("查看屏保信息接口:/v1/screen/findAll userId=", claims.get("userId"));
        SysScreen screen = screenService.find();
        return ResponseMessage.ok(screen);
    }


}
