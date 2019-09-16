package com.wj;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.helper.impl.RedisHelperImpl;
import com.wj.core.service.qst.Qst;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {
    static JsonMapper mapper = JsonMapper.defaultMapper();
    @Autowired
    private RedisHelperImpl redisHelper;
    @Test
    public void contextLoads() {
//        redisHelper.remove("experience_5");
//        for (int i = 0; i < 1; i++) {
//        ExperienceCode experienceCode = new ExperienceCode();
//        String a = "1111111111";
//        String b = "2222222222";
//        String c = "3333333333";
//        redisHelper.listPush("experience_5", a);
//        redisHelper.listPush("experience_5", b);
//        redisHelper.listPush("experience_5", c);
//        }
        Object obj = redisHelper.listFindAll("experience_9");
        System.out.println(obj);
        String code = (String) redisHelper.listLPop("experience_9");
        System.out.println(code);
        System.out.println(code.replaceAll("\"",""));
//        Object o = redisHelper.listLPop("experience_1");
//        System.out.println(o);
//        String userName = "18310800479";
//        Map<String, Object> requestParam = Maps.newHashMap();
//        requestParam.put("DevUserName",userName);
//        requestParam.put("Mobile",userName);
//        String url = Qst.URL9700 + "agentregister";
//        String result = null;
//        try {
//            result = HttpClients.postObjectClientJsonHeaders(url, Qst.TOKEN, requestParam);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (result.equals("405 Method Not Allowed")) {
//            System.out.println("进来了");
//        }
//        Map<String, Object> qst = mapper.fromJson(result, Map.class);
//        System.out.println(qst);
//        System.out.println("111111111");
    }

//    @Value("${wj.oss.key}")
//    private String key;
//    @Value("${wj.oss.secret}")
//    private String secret;
//    //产品名称:云通信短信API产品,开发者无需替换
//    static final String product = "Dysmsapi";
//    //产品域名,开发者无需替换
//    static final String domain = "dysmsapi.aliyuncs.com";
//    @Test
//    public void contextLoads() {
////        String s = "{\"showapi_res_error\": \"\",\n" +
////                "            \"showapi_res_id\": \"961dcbd9e768422d9b1e19cf4b78163d\",\n" +
////                "            \"showapi_res_body\": {\n" +
////                "                \"area\": \"北京\",\n" +
////                "                \"areaid\": \"101010100\",\n" +
////                "                \"showapi_fee_code\": -1,\n" +
////                "                \"ret_code\": 0,\n" +
////                "            },\n" +
////                "            \"showapi_res_code\": 0}";
////
////        JSONObject jsonObject = JSONObject.parseObject(s);
////        System.out.println(jsonObject);
////        String r = jsonObject.getString("showapi_res_body");
//////        String lastResult = r.substring(1,r.length()-1);
////        System.out.println(r);
////        System.out.println(lastResult);
//
//
//        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing", key, secret);
//        DefaultProfile.addEndpoint("cn-beijing", product, domain);
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("PhoneNumbers","18310800479");
//        request.putQueryParameter("SignName","吾家智工");
//        request.putQueryParameter("TemplateCode","SMS_166655952");
//        request.putQueryParameter("TemplateParam", "{\"code\":\"123456\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            JSONObject jsonObject = JSONObject.parseObject(response.getData());
//            String a = jsonObject.getString("Message");
//            System.out.println(a);
//            System.out.println("-------------");
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//
//    }

}
