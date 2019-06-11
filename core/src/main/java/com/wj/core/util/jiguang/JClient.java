package com.wj.core.util.jiguang;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JClient {

    protected static final Logger LOG = LoggerFactory.getLogger(JClient.class);

    private static final String appKey = "015fcb905190dcfe7d641ce4";
    private static final String masterSecret = "1e18d08829e9f4aff2cc1226";

//    public static void main(String[] args) {
//        System.out.println(getCustomPushClient());
//    }

    public static JPushClient getDefaultClient() {
        JPushClient client = new JPushClient(masterSecret, appKey);
        return client;
    }

    public static JPushClient getCustomClient() {
        ClientConfig config = ClientConfig.getInstance();
        config.setMaxRetryTimes(5);
        config.setConnectionTimeout(10 * 1000);	// 10 seconds
        config.setSSLVersion("TLSv1.1");		// JPush server supports SSLv3, TLSv1, TLSv1.1, TLSv1.2

        JPushClient jPushClient = new JPushClient(masterSecret, appKey, null, config);
        return jPushClient;
    }

    public static JPushClient getCustomPushClient() {
        ClientConfig config = ClientConfig.getInstance();

        config.setApnsProduction(false); 	// development env
        config.setTimeToLive(60 * 60 * 24); // one day

        //	config.setGlobalPushSetting(false, 60 * 60 * 24); // development env, one day

        JPushClient jPushClient = new JPushClient(masterSecret, appKey, null, config); 	// JPush client

        //	PushClient pushClient = new PushClient(masterSecret, appKey, null, config); 	// push client only
        return jPushClient;
    }

}
