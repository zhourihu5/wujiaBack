package com.wj.api.controller.apply;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.OpenDoorService;
import com.wj.core.service.user.UserFamilyService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "/v1/apply", tags = "申请开锁接口模块")
@RestController
@RequestMapping("/v1/apply/")
public class ApplyLockController {

    public final static Logger logger = LoggerFactory.getLogger(ApplyLockController.class);

    @Autowired
    private ApplyLockService applyLockService;

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private BaseFamilyService familyService;

    @Autowired
    private OpenDoorService openDoorService;

    @Autowired
    private BaseUnitRepository baseUnitRepository;

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

    @ApiOperation(value = "远程开锁", notes = "远程开锁")
    @GetMapping("openDoor")
    public ResponseMessage openDoor() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        String userName = (String) claims.get("userName");;
        List<SysUserFamily> userFamilyList = userFamilyService.findByUserId(userId);
        //全视通
        for (SysUserFamily userFamily : userFamilyList) {
            BaseFamily baseFamily = familyService.findByFamilyId(userFamily.getUserFamily().getFamilyId());
            String unitCode = baseFamily.getCode().substring(0, 16);
            BaseUnit baseUnit = baseUnitRepository.findByUnitCode(unitCode);
            Map<String, Object> result = openDoorService.openDoor(userName, baseUnit.getDirectory());
            if (Integer.valueOf(result.get("Code").toString()) != 200) {
                throw new ServiceException("同步全视通数据错误", ErrorCode.QST_ERROR);
            }
        }
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取临时开锁密码", notes = "获取临时开锁密码")
    @GetMapping("generalSecretCodeWithOpenDoor")
    public ResponseMessage<String> generalSecretCodeWithOpenDoor(String communtityCode) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        String userName = (String) claims.get("userName");
        return ResponseMessage.ok(openDoorService.SecretCodeWithOpenDoor(communtityCode, userId, userName));
    }

}
