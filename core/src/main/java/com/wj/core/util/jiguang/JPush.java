package com.wj.core.util.jiguang;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.wj.core.util.jiguang.enums.PushType;
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


    // 使用 NettyHttpClient 异步接口发送请求
    public static void sendPushWithCallback(PushType type, String content, List<String> alias) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = null;
            switch (type) {
                case SYS :
                    payload = buildPushMessageAsALl(type.getName(), content);
                    break;
                default :
                    payload = buildPushMessageAsAlias(alias, type.getName(), content);
            }
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, responseWrapper -> LOG.info("Got result: " + responseWrapper.responseContent));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void sendPushAll() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushMessageAsALl("CARD", "test1");
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, responseWrapper -> LOG.info("Got result: " + responseWrapper.responseContent));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void main(String[] args) {
        sendPushAll();
    }


    public static PushPayload buildPushMessageAsAlias(List<String> alias, String type, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder().setContentType(type).setMsgContent(content).build())
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                .build();
    }


    public static PushPayload buildPushMessageAsALl(String type, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder().setContentType(type).setMsgContent(content).build())
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                .build();
    }


}
