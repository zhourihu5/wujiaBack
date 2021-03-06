package com.wj.core.service.qst;

import com.google.common.collect.Maps;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.service.apply.ApplyLockService;
import com.wj.core.service.base.BaseFamilyService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OpenDoorService {

    static JsonMapper mapper = JsonMapper.defaultMapper();

    @Autowired
    private ApplyLockService applyLockService;

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private BaseFamilyService baseFamilyService;

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    @Autowired
    private BaseUnitRepository baseUnitRepository;

    //远程开锁 用户明，单元编号
    public Map<String, Object> openDoor(String userName, String deviceDirectory) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + Qst.TOKEN);
        String url = Qst.URL21664 + "RemoteUnlock/OpenDoor?tenantCode=" + Qst.TC + "&devUserName=" + userName + "&deviceDirectory=" + deviceDirectory;
        String object = null;
        try {
            object = HttpClients.getObjectClientAndHeaders(url, requestHeaders);
        } catch (Exception e) {
            throw new ServiceException("门口机未绑定", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Map<String, Object> qst = mapper.fromJson(object, Map.class);
        return qst;
    }
    //获取门禁出入记录
    public Map<String, Object> accessRecords(String userName, String deviceDirectory, Integer pageNum) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + Qst.TOKEN);
        String url = Qst.URL21664 + "accessrecords?tenantCode=" + Qst.TC
                + "&devUserName=" + userName
                + "&DeviceLocalDirectory=" + deviceDirectory
                + "&pageNum=" + pageNum
                ;
//        String object = HttpClients.getObjectClientAndHeaders(url, requestHeaders);
//        object=new String(object.getBytes(), new GBK());//乱码，转码试试？？

        byte[]bytes= HttpClients.getBytes(url, requestHeaders);
        String object=new String(bytes);

        Map<String, Object> qst = mapper.fromJson(object, Map.class);
        qst.put("imgUrl",Qst.IMG_URL);
        return qst;
    }

    //临时密码开锁
    public Map<String, Object> SecretCodeWithOpenDoor(String communtityCode, Integer fid, Integer userId, String userName) {
        BaseFamily family = baseFamilyRepository.findByFamilyId(fid);
        BaseUnit unit = baseUnitRepository.findByUnitId(family.getUnitId());
        String deviceLocalDirectory = unit.getDirectory();
        BaseCommuntity communtity = baseFamilyService.findCommuntityByFamilyId(fid);
        String address = communtity.getName();
        String appaccesstokenUrl = Qst.URL9700 + "appaccesstoken?devUserName=" + userName;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic " + Qst.UUID);
        String obj = HttpClients.getObjectClientAndHeaders(appaccesstokenUrl, requestHeaders);
//        String appaccesstokenUrl = Qst.URL9700 + "accesstoken?userName=" + Qst.USERNAME + "&password=" + Qst.PASSWORD;
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("Authorization", "Basic " + Qst.UUID);
//        requestHeaders.add("Content-Type", "application/x-www-form-urlencoded");
//        String obj = HttpClients.getObjectClientAndHeaders(appaccesstokenUrl, requestHeaders);
        Map<Object, Object> tokenResult = mapper.fromJson(obj, Map.class);
        String url = Qst.URL21664 + "SecretCodeWithOpenDoor";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = formatter.format(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 8);// 24小时制
        date = cal.getTime();
        String endDate = formatter.format(date);
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("DeviceLocalDirectory", deviceLocalDirectory+"-1");
        maps.put("ValidStartTime", startDate);
        maps.put("ValidEndTime", endDate);
        maps.put("MaxAvailableTimes", 30);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(maps);
//        Map<String, Object>[] map = new Map[1];
//        map[0] = maps;
        Map<String, Object> requestParam = Maps.newHashMap();
        requestParam.put("TenantCode", Qst.TC);
        requestParam.put("SecretCodeInfos", list);
//        System.out.println(JSONArray.fromObject(list));
        String object = HttpClients.postObjectClientJsonHeaders(url, tokenResult.get("access_token").toString(), requestParam);
        Map<String, Object> result = mapper.fromJson(object, Map.class);
//        if (Integer.valueOf(result.get("Code").toString()) != 200) {
//            throw new ServiceException("数据异常", ErrorCode.QST_ERROR);
//        }
        Map<String, Object> codeResult = new HashMap<>();
        codeResult.put("code",result.get("SecretCode").toString());
        codeResult.put("address", address);
        codeResult.put("endDate", endDate);
        return codeResult;
    }


}

