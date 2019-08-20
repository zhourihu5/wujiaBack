package com.wj.api.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.wj.api.controller.user.LoginController;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.util.wx.PayUtil;
import com.wj.core.util.wx.WechatConfig;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "支付模块")
@RestController
@RequestMapping("/wx/")
public class PayController {
    public final static Logger logger = LoggerFactory.getLogger(PayController.class);


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @ApiOperation(value = "请求支付接口")
    @RequestMapping(value = "/wxPay", method = RequestMethod.POST)
    public ResponseMessage<Object> wxPay(HttpServletRequest request, @RequestBody OrderInfo orderInfo) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        OrderInfo orderInfo0 = orderInfoRepository.findByUserIdAndActivityId(userId, orderInfo.getActivityId());
        if (orderInfo0 != null) {
            throw new ServiceException("此次活动每人限制一单!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        OrderInfo orderInfo1 = orderService.findOrderByCode(orderInfo.getCode());
        if (null == orderInfo1) {
            throw new ServiceException("订单错误!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (!orderInfo1.getStatus().equals("1")) {
            throw new ServiceException("此订单不能支付", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        // 判断下单时间是否超时
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(date);
        Date afterDate = new Date(orderInfo1.getCreateDate().getTime() + 900000);
        String orderDate = formatter.format(afterDate);
//        boolean isbefore = isDateBefore(currentTime,endDate);
        boolean isafter = isDateAfter(currentTime, orderDate);
        if (isafter) {
            throw new ServiceException("订单已超时，不能支付!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        String money = orderInfo1.getRealPrice().multiply(new BigDecimal(100)).intValue() + "";
        SysUserInfo userInfo = userInfoService.findUserInfo(userId);
        String openid = userInfo.getWxOpenId();
        try {
            //生成的随机字符串
            String nonce_str = getRandomStringByLength(32);
            //商品名称
            String body = "测试商品名称";
            //获取客户端的ip地址
            String spbill_create_ip = getIpAddr(request);

            //组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = new HashMap<>();
            packageParams.put("appid", WechatConfig.appid);
            packageParams.put("mch_id", WechatConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderInfo1.getCode());//商户订单号,自己的订单ID
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WechatConfig.notify_url);//支付成功后的回调地址
            packageParams.put("trade_type", WechatConfig.TRADETYPE);//支付方式
            packageParams.put("openid", openid + "");//用户的openID，自己获取

            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, WechatConfig.key, "utf-8").toUpperCase();

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + WechatConfig.appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + WechatConfig.mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + WechatConfig.notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + orderInfo1.getCode() + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"//支付的金额，单位：分
                    + "<trade_type>" + WechatConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            System.out.println(xml);
            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(WechatConfig.pay_url, "POST", xml);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码
            String result_code = (String) map.get("result_code");//返回状态码

            Map<String, Object> response = new HashMap<String, Object>();//返回给小程序端需要的参数
            System.out.println(return_code == "SUCCESS");
            System.out.println(return_code.equals(result_code));
            if (return_code.equals("SUCCESS") && return_code.equals(result_code)) {
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                String stringSignTemp = "appId=" + WechatConfig.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WechatConfig.key, "utf-8").toUpperCase();

                response.put("paySign", paySign);
            }

            response.put("appid", WechatConfig.appid);

            return ResponseMessage.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //这里是支付回调接口，微信支付成功后会自动调用
    @RequestMapping(value = "/wxNotify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("支付回调---------------------------");
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";

        Map map = PayUtil.doXMLParse(notityXml);
        logger.info(map+"-------------");
        System.out.println("mapmapmapmapmapmapmapmapmapmapmapmapmap--------");
        String returnCode = (String) map.get("return_code");
        logger.info(returnCode+"---------returnCode----");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
            logger.info(validParams+"---------validParams----");
            String prestr = PayUtil.createLinkString(validParams);
            logger.info(prestr+"------------prestr1-");
            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            logger.info(PayUtil.verify(prestr, (String) map.get("sign"), WechatConfig.key, "utf-8")+"------------prestr2-");
            if (PayUtil.verify(prestr, (String) map.get("sign"), WechatConfig.key, "utf-8")) {
                /**此处添加自己的业务逻辑代码start**/
                // 平台订单号
                String code = (String) map.get("out_trade_no");
                // 微信订单号
                String wxOrderCode = (String) map.get("transaction_id");
                orderService.updateWxOrderByCode(code, wxOrderCode);
                //注意要判断微信支付重复回调，支付成功后微信会重复的进行回调
                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    //获取随机字符串
    private String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //获取IP
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isDateAfter(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).after(df.parse(date2));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}