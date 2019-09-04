package com.wj.core.service.sendMessage;

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
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.Qst;
import com.wj.core.util.HttpClients;
import com.wj.core.util.mapper.JsonMapper;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class YunpianSendSms {

    final String url = "https://sms.yunpian.com/v2/sms/single_send.json";
    final String apikey = "fd07314aec69a7be384ca015e96026f4";
    static JsonMapper mapper = JsonMapper.defaultMapper();


    public Integer sendMessage(String userName, String smsCode) {
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient(apikey).init();
        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, userName);
        param.put(YunpianClient.TEXT, "【吾家】您的验证码是" + smsCode + "。如非本人操作，请忽略本短信");
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        clnt.close();
        return r.getCode();
    }

    public Integer sendApply(String userName, String name) {
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient(apikey).init();
        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, userName);
        param.put(YunpianClient.TEXT, "【吾家】您申请入住的" + name + "已审核通过，快到吾家小程序中查看吧！");
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        clnt.close();
        return r.getCode();
    }

    public Integer sendDelivery(String userName, String name) {
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient(apikey).init();
        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, userName);
        param.put(YunpianClient.TEXT, "【吾家】亲～您购买的" + name + "已开始配送，请注意查收哦～");
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        clnt.close();
        return r.getCode();
    }
}
