package com.wj.core.service.qst;

import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class QstCommuntityService {

    @Value("${qst.info.api}")
    private String API;

    //创建社区
    public Object tenantvillages() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String accessToken = request.getHeader("Authorization");
            Map<String, Object> requestParam = new HashMap<>();
            requestParam.put("TenantCode", "T0001");
            requestParam.put("AreaID", "110000000000");
            requestParam.put("VillageName", "繁华测试1");
            String url = API + "tenantvillages";
            Object object = HttpClients.postObjectClientJsonHeaders(url, accessToken, requestParam);
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //配置小区位长信息
    public Object TenantStructureDefinition() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String accessToken = request.getHeader("Authorization");
            Map<String, Object> requestParam = new HashMap<>();
            requestParam.put("TenantCode", "T0001");
            requestParam.put("StructureID", 1752);
            requestParam.put("Period", 1);
//            requestParam.put("Region", 1);
            requestParam.put("Building", 2);
            requestParam.put("Unit", 2);
            requestParam.put("Floor", 2);
            requestParam.put("Room", 2);
            String url = API + "tenantvillages";
            Object object = HttpClients.postObjectClientJsonHeaders(url, accessToken, requestParam);
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //添加小区节点
    public Object tenantstructures() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String accessToken = request.getHeader("Authorization");
            Map<String, Object> requestParam = new HashMap<>();
            requestParam.put("TenantCode", "T0001");
            requestParam.put("AreaID", "110000000000");
            requestParam.put("VillageName", "繁华测试000");
            String url = API + "tenantvillages";
            Object object = HttpClients.postObjectClientJsonHeaders(url, accessToken, requestParam);
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //添加楼栋（单元门）
    public Object tenantunitdoors() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String accessToken = request.getHeader("Authorization");
            Map<String, Object> requestParam = new HashMap<>();
            requestParam.put("TenantCode", "T0001");
            requestParam.put("ParentDirectory", "1");
            requestParam.put("NodeNum", 3);
//            requestParam.put("Attribute", 2);
            requestParam.put("NodeDisplay", "期");
            requestParam.put("NodeNumStart", 1);
            String url = API + "tenantvillages";
            Object object = HttpClients.putObjectClientJsonHeaders(url, accessToken, requestParam);
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    //创建小区全部结构
    public Object tenantstructures(String accessToken, Map<String, Object> requestParam) {
        String url = API + "tenantstructures";
        Object object = HttpClients.postObjectClientJsonHeaders(url, accessToken, requestParam);
        return object;
    }




}

