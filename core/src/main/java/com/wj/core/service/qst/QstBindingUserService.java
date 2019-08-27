package com.wj.core.service.qst;

import com.google.common.collect.Maps;
import com.wj.core.service.qst.dto.QstResult;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QstBindingUserService {

    static JsonMapper mapper = JsonMapper.defaultMapper();

    // 同步用户到全视通
    public Map<String, Object> agentregister(String userName) {
        Map<String, Object> requestParam = Maps.newHashMap();
        requestParam.put("DevUserName", userName);
        requestParam.put("Mobile", userName);
        String url = Qst.URL9700 + "agentregister";
        String result = HttpClients.postObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
        if (result.equals("405 Method Not Allowed")) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("Code","201");
            return map;
        }
        Map<String, Object> qst = mapper.fromJson(result, Map.class);
        return qst;
    }

    //用户绑定家庭 用户名，家庭编码
    public Map<String, Object> userRooms(String userName, String structureDirectory) {
        Map<String, Object> requestParam = Maps.newHashMap();
        requestParam.put("TenantCode", Qst.TC);
        requestParam.put("UserName", userName);
        String url = Qst.URL21664 + "UserRooms?structureDirectory=" + structureDirectory;
        String result = HttpClients.putObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
        Map<String, Object> qst = mapper.fromJson(result, Map.class);
        return qst;
    }


}

