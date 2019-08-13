package com.wj.core.service.qst;

import com.wj.core.service.qst.dto.QstResult;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

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


}

