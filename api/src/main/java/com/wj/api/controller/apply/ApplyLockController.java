package com.wj.api.controller.apply;

import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseCommuntityLock;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.base.BaseCommunityLockRepository;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.OpenDoorService;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.util.number.NumberUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
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

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    @Autowired
    BaseCommunityLockRepository baseCommunityLockRepository;

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
    public ResponseMessage openDoor(String communtityCode, Integer fid,@RequestParam(defaultValue = "0") Double longitude,@RequestParam(defaultValue = "0") Double latitude) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        String userName = (String) claims.get("userName");
        BaseFamily family = familyService.findByFamilyId(fid);
        BaseCommuntity baseCommuntity= familyService.findCommuntityByFamilyId(fid);
//        double longitude = 0;
//        double latitude = 0;
       List<BaseCommuntityLock>list= baseCommunityLockRepository.findByCommuntityId(baseCommuntity.getId());


        BaseCommuntityLock baseCommuntityLock=null;
        double velve=7;
       if(!CollectionUtils.isEmpty(list)) {
           for (BaseCommuntityLock it: list) {//找出阀值距离内距离最近的门
               double distance=NumberUtil.LantitudeLongitudeDist(longitude,latitude,it.getLongitude().doubleValue(),it.getLatitude().doubleValue());
               if(distance<velve){//todo 距离计算不知道是否准确，可以替换为第三方服务计算
                   baseCommuntityLock=it;
                   velve=distance;
               }
           }
       }
        String deviceLocalDirectory=null;
        if(baseCommuntityLock!=null){
            deviceLocalDirectory=baseCommuntityLock.getDirectory();
            //todo 围墙机的设备编号
       }else {
            BaseUnit unit = baseUnitRepository.findByUnitId(family.getUnitId());
            deviceLocalDirectory = unit.getDirectory();
            if (deviceLocalDirectory == "") {
                throw new ServiceException("系统异常", ErrorCode.QST_ERROR);
            }
            deviceLocalDirectory = deviceLocalDirectory+"-1";
       }

        Map<String, Object> result = openDoorService.openDoor(userName, deviceLocalDirectory);
        if (Integer.valueOf(result.get("Code").toString()) != 200) {
            throw new ServiceException("数据异常", ErrorCode.QST_ERROR);
        }
        return ResponseMessage.ok();
    }
    @ApiOperation(value = "获取门禁出入记录", notes = "获取门禁出入记录")
    @GetMapping("accessRecords")
    public ResponseMessage accessRecords(Integer fid,@RequestParam(defaultValue = "1") Integer pageNum) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        String userName = (String) claims.get("userName");
        BaseFamily family = familyService.findByFamilyId(fid);
        BaseUnit unit = baseUnitRepository.findByUnitId(family.getUnitId());
        String deviceLocalDirectory = unit.getDirectory();
        if (deviceLocalDirectory == "") {
            throw new ServiceException("系统异常", ErrorCode.QST_ERROR);
        }
        deviceLocalDirectory = deviceLocalDirectory+"-1";
        Map<String, Object> result = openDoorService.accessRecords(userName, deviceLocalDirectory,pageNum);
        logger.info("accessRecords pageNum: {}",pageNum);

        return ResponseMessage.ok(result);
    }

    @ApiOperation(value = "获取临时开锁密码", notes = "获取临时开锁密码")
    @GetMapping("secretCodeWithOpenDoor")
    public ResponseMessage<Map<String, Object>> secretCodeWithOpenDoor(String communtityCode, Integer fid) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        String userName = (String) claims.get("userName");
        return ResponseMessage.ok(openDoorService.SecretCodeWithOpenDoor(communtityCode,fid, userId, userName));
    }

}
