package com.wj.core.util.jiguang;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.schedule.ScheduleResult;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class JPush {


    protected static final Logger LOG = LoggerFactory.getLogger(JPush.class);

    protected static final String APP_KEY = "8bc0518b5aa55c176ffbcbcb";
    protected static final String MASTER_SECRET = "52251687bfd0b5dcebda580d";
    protected static final String CARD_TYPE = "CARD";
    protected static final String MSG_TYPE = "MSG";
    protected static final String ADV_TYPE = "ADV";
    protected static final String MSG_CONTENT = "message custom push";


    // 使用 NettyHttpClient 异步接口发送请求
    public static void sendMsgPush(List<String> tags) {
        sendPushAll(tags, MSG_TYPE, MSG_CONTENT);
    }

    public static void sendPushAll(List<String> tags, String type, String content) {
        LOG.info("send user : " + tags.toString());
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushMessageAsTag(tags, type, content);

            client.sendRequest(HttpMethod.POST, payload.toString(), uri, responseWrapper -> LOG.info("Got result: " + responseWrapper.responseContent));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void sendCardSchedulePush(String scheduleName, String time, String content) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushPayload payload = buildPushMessageAsALl(CARD_TYPE, content);
            ScheduleResult result = jpushClient.createSingleSchedule(scheduleName, time, payload);
            LOG.info("schedule result is " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void sendPushAll(String type, String content) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushMessageAsALl(type, content);
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, responseWrapper -> LOG.info("Got result: " + responseWrapper.responseContent));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static String sendAdvSchedulePush(List<String> tags, String name, String start, String end,String time, String content) {
        LOG.info("schedule result is start time : " + start + " end time :" + end + " send time :"  + time + " content : " + content);
        LOG.info("send user :" + tags.toString());
        JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        PushPayload push = buildPushMessageAsTag(tags, ADV_TYPE, content);
        try {
            ScheduleResult result = jPushClient.createDailySchedule(name, start, end, time, push);
            LOG.info("schedule result is " + result);
            return result.getSchedule_id();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return "";
    }

    public static void deleteSchedule(String scheduleId) {
        //String scheduleId = "95bbd066-3a88-11e5-8e62-0021f652c102";
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);

        try {
            jpushClient.deleteSchedule(scheduleId);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void main(String[] args) {
        sendPushAll("MARKET", "1");
    }


    // 单个用户推送
    public static PushPayload buildPushMessageAsAlias(List<String> alias, String type, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder().setContentType(type).setMsgContent(content).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }


    public static PushPayload buildPushMessageAsALl(String type, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder().setContentType(type).setMsgContent(content).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }

    // 社区群推 community_id
    public static PushPayload buildPushMessageAsTag(List<String> tags, String type, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tags))
                .setMessage(Message.newBuilder().setContentType(type).setMsgContent(content).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }


}
