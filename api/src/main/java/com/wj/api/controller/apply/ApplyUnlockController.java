package com.wj.api.controller.apply;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.apply.ApplyUnlock;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.service.address.AddressService;
import com.wj.core.service.apply.ApplyUnlockService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/v1/apply", tags = "申请开锁接口模块")
@RestController
@RequestMapping("/v1/apply/")
public class ApplyUnlockController {

    public final static Logger logger = LoggerFactory.getLogger(ApplyUnlockController.class);

    @Autowired
    private ApplyUnlockService applyUnlockService;

    @ApiOperation(value = "申请开锁", notes = "申请开锁")
    @PostMapping("applyUnLock")
    public ResponseMessage applyUnLock(ApplyUnlock applyUnlock) {
        applyUnlockService.saveApplyUnlock(applyUnlock);
        return ResponseMessage.ok();
    }
}
