package com.wj.core.service.qst;

import com.google.common.collect.Maps;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.qst.dto.QstResult;
import com.wj.core.service.qst.dto.TenantvillagesDTO;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import org.apache.commons.lang.StringUtils;
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

    static JsonMapper mapper = JsonMapper.defaultMapper();

    //创建社区
    public Map<String, Object> tenantvillages(String areaId, String comName) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("TenantCode", Qst.TC);
        requestParam.put("AreaID", StringUtils.rightPad(areaId, 12, "0"));
        requestParam.put("VillageName", comName);
        String url = Qst.URL21664 + "tenantvillages";
        String result = HttpClients.postObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
        Map<String, Object> t = mapper.fromJson(result, Map.class);
        return t;
    }

    public static void main(String[] args) {
        String str = "{\"StructureID\": 1783,\"AreaID\": 440402000000,\"Directory\": \"21\",\"VillageName\": \"大井社区\",\"Attribute\": 1}";
        JsonMapper mapper = JsonMapper.defaultMapper();

        Map<String, Object> t = mapper.fromJson(str, Map.class);
        System.out.println(t.get("AreaID"));
    }

    //配置小区位长信息
    public QstResult tenantStructureDefinition(Integer structureID) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("TenantCode", Qst.TC);
        requestParam.put("StructureID", structureID); //小区结构ID(注：一定是小区的小区结构ID)
        requestParam.put("Period", 2); //期对应的位长配置
        requestParam.put("Region", 2); // 区对应的位长配置
        requestParam.put("Building", 2); //栋对应的位长配置
        requestParam.put("Unit", 2); // 单元对应的位长配置
        requestParam.put("Floor", 2); //层对应的位长配置
        requestParam.put("Room", 2); // 房对应的位长配置
        String url = Qst.URL21664 + "TenantStructureDefinition";
        String object = HttpClients.postObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
        QstResult qst = mapper.fromJson(object, QstResult.class);
        return qst;
    }

    //添加小区节点期 Attribute:2、区 Attribute:2、层不传、房：Attribute:10
    public String tenantstructures(String parentDirectory, Integer nodeNum, String nodeDisplay, Integer nodeStart, Integer attribute) {
        try {
            Map<String, Object> requestParam = Maps.newHashMap();
            requestParam.put("TenantCode", Qst.TC);
            requestParam.put("ParentDirectory", parentDirectory);
            requestParam.put("NodeNum", nodeNum);
            requestParam.put("NodeDisplay", nodeDisplay);
            requestParam.put("NodeStart", nodeStart);
            if (attribute != null) {
                requestParam.put("Attribute", 2);
            }
            String url = Qst.URL21664 + "tenantstructures";
            String result = HttpClients.postObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //添加楼栋（单元门）
    public String tenantunitdoors(String parentDirectory, Integer buildingNum, Integer unitNum, Integer buildStartNum, Integer unitStartNum) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("TenantCode", Qst.TC);
        requestParam.put("ParentDirectory", parentDirectory);
        requestParam.put("BuildingNum", buildingNum);
        requestParam.put("UnitNum", unitNum);
        requestParam.put("BuildingNumStart", buildStartNum);
        requestParam.put("UnitNumStart", unitStartNum);
        requestParam.put("BuildingDisplay", "栋");
        requestParam.put("UnitDisplay", "单元");
        String url = Qst.URL21664 + "tenantunitdoors";
        String object = HttpClients.putObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
        return object;
    }


}

