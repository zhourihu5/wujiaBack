package com.wj.core.service.qst;

import com.wj.core.util.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenDoorService {

    @Value("${qst.info.api}")
    private String API;

    //远程开锁
    public Object OpenDoor() {
        String tenantCode = "T0001";
        String devUserName = "繁华";
        String deviceDirectory = "1-1";
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String accessToken = request.getHeader("Authorization");
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Authorization", "Bearer " + accessToken);
            String url = API + "tenantvillages?tenantCode=" + tenantCode + "&devUserName=" + devUserName +"&deviceDirectory="+deviceDirectory;
            Object object = HttpClients.getObjectClientAndHeaders(url, requestHeaders);
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



}

