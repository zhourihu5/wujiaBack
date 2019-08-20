package com.wj.core.util.wx;

public class WechatConfig {
    //小程序appid
    public static final String appid = "wx78aa6144c22e65ae";
    //微信支付的商户id
    public static final String mch_id = "1549473651";
    //微信支付的商户密钥
    public static final String key = "beijingwujiawangluokejiyouxiangs";
    //支付成功后的服务器回调url，这里填PayController里的回调函数地址
    public static final String notify_url = "http://mapi.home-guard.cn/wx/wxNotify";
//    public static final String notify_url = "http://192.168.1.18:8081/wx/wxNotify";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String red_pay_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
}
