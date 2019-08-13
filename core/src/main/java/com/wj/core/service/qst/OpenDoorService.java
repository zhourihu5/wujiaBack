package com.wj.core.service.qst;

import com.google.common.collect.Maps;
import com.wj.core.service.qst.dto.QstResult;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenDoorService {

    static JsonMapper mapper = JsonMapper.defaultMapper();

    //远程开锁 用户明，单元编号
    public QstResult openDoor(String userName, String deviceDirectory) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + Qst.TOKEN);
        String url = Qst.URL21664 + "RemoteUnlock/OpenDoor?tenantCode=" + Qst.TC + "&devUserName=" + userName + "&deviceDirectory=" + deviceDirectory;
        String object = HttpClients.getObjectClientAndHeaders(url, requestHeaders);
        QstResult qst = mapper.fromJson(object, QstResult.class);
        return qst;
    }

    //临时密码开锁
    public Map<String, Object> generalSecretCodeWithOpenDoor() {
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("Authorization", "Bearer " + Qst.TOKEN);
        String[] a = {"1111","2222"};
        String url = Qst.URL21664 + "generalSecretCodeWithOpenDoor";
        Map<String, Object> requestParam = Maps.newHashMap();
        requestParam.put("TenantCode", Qst.TC);
        requestParam.put("DeviceLocalDirectoryArray", a);
        requestParam.put("Password", "");
        requestParam.put("RequestID", "");
        requestParam.put("ValidStartTime", "startDate");
        requestParam.put("ValidEndTime", "endDate");
        requestParam.put("MaxAvailableTimes", 30);
        String object = HttpClients.postObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
        Map<String, Object>  qst = mapper.fromJson(object, Map.class);
        return qst;
    }


}

