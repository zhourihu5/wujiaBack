package com.wj.api.controller.apply;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/v1/apply", tags = "申请开锁接口模块")
@RestController
@RequestMapping("/v1/apply/")
public class ApplyLockController {

    public final static Logger logger = LoggerFactory.getLogger(ApplyLockController.class);

    @Autowired
    private ApplyLockService applyLockService;

    @ApiOperation(value = "申请开锁", notes = "申请开锁")
    @PostMapping("applyUnLock")
    public ResponseMessage applyUnLock(@RequestBody ApplyLock applyLock) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        applyLock.setUserId(userId);
        Integer count = applyLockService.findByUserIdAndFamilyId(userId, applyLock.getFamilyId(), "1");
        if (count > 0) {
            throw new ServiceException("您已经申请过此小区，无需重复申请！", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        applyLockService.saveApplyLock(applyLock);
        return ResponseMessage.ok();
    }

}
