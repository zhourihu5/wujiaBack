package com.wj.core.service.wx;

import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.wx.RedPacket;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.wx.RedPacketRepository;
import com.wj.core.service.order.OrderService;
import com.wj.core.service.user.UserInfoService;
import com.wj.core.util.time.ClockUtil;
import com.wj.core.util.time.DateFormatUtil;
import com.wj.core.util.wx.PayUtil;
import com.wj.core.util.wx.WechatConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PayUserService {

    @Autowired
    private RedPacketRepository redPacketRepository;

//    @Autowired
//    private UserInfoService userInfoService;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private OrderInfoRepository orderInfoRepository;

    public Map wxPay(HttpServletRequest request, SysUserInfo sysUserInfo, OrderInfo orderInfo) {
        String openid = "oO7s75P31qgjWJrGSd4zAW0F2iqI";
//        String openid = sysUserInfo.getWxOpenId();
        try {
            //????????????????????????
            String nonce_str = getRandomStringByLength(32);
            //??????????????????ip??????
            String spbill_create_ip = getIpAddr(request);
            String scene_id = "PRODUCT_4";
            String str = getRandomStringByLength(10);
            String mch_billno = WechatConfig.mch_id + DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFALT_DATE, ClockUtil.currentDate()) + str;
//            String mch_billno = "20190816263vzhqbgck4sccevu";
            Integer total_num = 1;
            Integer total_amount = orderInfo.getPrice().multiply(BigDecimal.valueOf(100)).intValue();
            String send_name = "??????";
            String wishing = "????????????";
            String actName = "????????????";
            String remark = "????????????";

            //??????????????????????????????????????????????????????
            Map<String, String> packageParams = new HashMap<>();
            packageParams.put("nonce_str", nonce_str);
//            packageParams.put("sign", WechatConfig.mch_id);
            packageParams.put("mch_billno", mch_billno);
            packageParams.put("mch_id", WechatConfig.mch_id);
            packageParams.put("wxappid", WechatConfig.appid);
            packageParams.put("send_name", send_name);
            packageParams.put("scene_id", scene_id);
            packageParams.put("re_openid", openid);
            packageParams.put("total_amount", total_amount + "");
            packageParams.put("total_num", total_num + "");
            packageParams.put("wishing", wishing);
            packageParams.put("client_ip", spbill_create_ip);
            packageParams.put("act_name", actName);
            packageParams.put("remark", remark);

            String prestr = PayUtil.createLinkString(packageParams); // ???????????????????????????????????????=???????????????????????????&???????????????????????????

            //MD5??????????????????????????????????????????????????????????????????????????????
            String mysign = PayUtil.sign(prestr, WechatConfig.key, "utf-8").toUpperCase();

            //?????????????????????????????????xml?????????????????????????????????????????????????????????
            String xml = "<xml>"
                    + "<sign><![CDATA[" + mysign + "]]></sign>"
                    + "<mch_billno><![CDATA[" + mch_billno + "]]></mch_billno>"
                    + "<mch_id><![CDATA[" + WechatConfig.mch_id + "]]></mch_id>"
                    + "<wxappid><![CDATA[" + WechatConfig.appid + "]]></wxappid>"
                    + "<send_name><![CDATA[" + send_name + "]]></send_name>"
                    + "<re_openid><![CDATA[" + openid + "]]></re_openid>"
                    + "<total_amount><![CDATA[" + total_amount + "]]></total_amount>"
                    + "<total_num><![CDATA[" + total_num + "]]></total_num>"
                    + "<wishing><![CDATA[" + wishing + "]]></wishing>"
                    + "<client_ip><![CDATA[" + spbill_create_ip + "]]></client_ip>"
                    + "<act_name><![CDATA[" + actName + "]]></act_name>"
                    + "<remark><![CDATA[" + remark + "]]></remark>"
                    + "<scene_id><![CDATA[" + scene_id + "]]></scene_id>"
                    + "<nonce_str><![CDATA[" + nonce_str + "]]></nonce_str>"
//                    + "<risk_info>" + mysign + "</risk_info>"
                    + "</xml>";


            System.out.println(xml);
            //???????????????????????????
//            String contextPath = request.getSession().getServletContext().getRealPath("");
//            String certPath = contextPath.substring(0, contextPath.lastIndexOf("\\")) + wechatConfig.getCert();
            String certPath = "/Users/thz/Desktop/apiclient_cert.p12";
            System.out.println(certPath);
            String certPassword = WechatConfig.mch_id;
            String rets = certPost(WechatConfig.red_pay_url, xml, certPath, certPassword);
            System.out.println(rets);
            //???????????????????????????????????????????????????
            String result = PayUtil.httpRequest(WechatConfig.red_pay_url, "POST", xml);
            System.out.println(result);
            // ????????????????????????HashMap???
            Map map = PayUtil.doXMLParse(result);
            System.out.println(map);
            String return_code = (String) map.get("return_code");//???????????????
            String result_code = (String) map.get("result_code");//???????????????
            if (return_code.equals("SUCCESS") && return_code.equals(result_code)) {
                String send_listid = (String) map.get("send_listid");//???????????????
                RedPacket redPacket = new RedPacket();
                redPacket.setSendListid(send_listid);
                redPacket.setMoney(total_amount);
                redPacket.setOrderId(orderInfo.getId());
                redPacket.setOrderCode(orderInfo.getCode());
                redPacket.setUserId(orderInfo.getUserId());
                redPacket.setWxOpenid(sysUserInfo.getWxOpenId());
                redPacket.setWxAppid(WechatConfig.appid);
                redPacket.setCreateDate(new Date());
                redPacketRepository.save(redPacket);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String certPost(String url, String requestXML, String cretPath, String certPwd) throws NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        FileInputStream instream = new FileInputStream(new File(cretPath));
        System.out.println(certPwd.toCharArray());
        try {
            keyStore.load(instream, certPwd.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        instream.close();

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPwd.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        String result = "";
        try {

            HttpPost httpPost = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(requestXML, "UTF-8");
            // ????????????
            reqEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        result += text;
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

    //?????????????????????
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

    //??????IP
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //?????????????????????????????????ip???????????????ip????????????ip
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