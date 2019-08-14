package com.wj.core.service.qst;

import com.google.common.collect.Maps;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.dto.QstResult;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OpenDoorService {

    static JsonMapper mapper = JsonMapper.defaultMapper();

    @Autowired
    private ApplyLockService applyLockService;

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private BaseFamilyService familyService;

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    @Autowired
    private BaseUnitRepository baseUnitRepository;

    //远程开锁 用户明，单元编号
    public Map<String, Object> openDoor(String userName, String deviceDirectory) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + Qst.TOKEN);
        String url = Qst.URL21664 + "RemoteUnlock/OpenDoor?tenantCode=" + Qst.TC + "&devUserName=" + userName + "&deviceDirectory=" + deviceDirectory;
        String object = HttpClients.getObjectClientAndHeaders(url, requestHeaders);
        Map<String, Object> qst = mapper.fromJson(object, Map.class);
        return qst;
    }

    //临时密码开锁
    public String SecretCodeWithOpenDoor(String communtityCode, Integer userId, String userName) {
        String deviceLocalDirectory = "";
        List<SysUserFamily> userFamilyList = userFamilyService.findByUserId(userId);
        for (SysUserFamily userFamily : userFamilyList) {
            List<BaseFamily> baseFamilyList = baseFamilyRepository.findByFamilyIdAndCodeLike(userFamily.getUserFamily().getFamilyId(), communtityCode);
            if (baseFamilyList.size() > 0) {
                String unitCode = baseFamilyList.get(0).getCode().substring(0, 16);
                BaseUnit baseUnit = baseUnitRepository.findByUnitCode(unitCode);
                deviceLocalDirectory = baseUnit.getCode();
                break;
            }
        }
//        int random = (int) ((Math.random() * 9 + 1) * 100000);
        String url = Qst.URL21664 + "generalSecretCodeWithOpenDoor";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = formatter.format(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 8);// 24小时制
        date = cal.getTime();
        String endDate = formatter.format(date);
        Map<String, Object> requestParam = Maps.newHashMap();
        requestParam.put("TenantCode", Qst.TC);
        requestParam.put("DeviceLocalDirectory", deviceLocalDirectory);
//        requestParam.put("Password", random + "");
//        requestParam.put("RequestID", userName);
        requestParam.put("ValidStartTime", startDate);
        requestParam.put("ValidEndTime", endDate);
        requestParam.put("MaxAvailableTimes", 30);
        String object = HttpClients.postObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
        Map<String, Object> result = mapper.fromJson(object, Map.class);
        return result.get("SecretCode").toString();
    }


}

