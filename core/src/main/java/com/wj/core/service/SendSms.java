package com.wj.core.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;

import java.util.Date;

@Service
public class SendSms {
    @Value("${wj.oss.key}")
    private String key;
    @Value("${wj.oss.secret}")
    private String secret;
//    //产品名称:云通信短信API产品,开发者无需替换
//    @Value("${wj.send.product}")
//    private String product;
    //产品域名,开发者无需替换
    @Value("${wj.send.domain}")
    private String domain;
    @Value("${wj.send.action}")
    private String action;
    @Value("${wj.send.version}")
    private String version;
    @Value("${wj.send.SignName}")
    private String SignName;
    @Value("${wj.send.TemplateCode}")
    private String TemplateCode;
    @Value("${wj.send.TemplateCodeApply}")
    private String TemplateCodeApply;
    @Value("${wj.send.TemplateCodeDelivery}")
    private String TemplateCodeDelivery;

    public String send(String phoneNumbers, String smsCode) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing", key, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + smsCode + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            String message = jsonObject.getString("Message");
            return message;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendApply(String phoneNumbers, String name) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing", key, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCodeApply);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("TemplateParam", "{\"name\":\"" + name + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            String message = jsonObject.getString("Message");
            return message;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendDelivery(String phoneNumbers) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing", key, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCodeDelivery);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            String message = jsonObject.getString("Message");
            return message;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}
