package com.wj.core.service.wx;

import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.HttpClients;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class WxSignService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Value("${wj.wx.appid}")
    private String appid;
    @Value("${wj.wx.secret}")
    private String secret;

    public void ceshi() {
        String appid = "wxf3ae33747a5bd16c";
        String secret = "3083ab3b68567e2f0f341e9fa28db02e";

        //System.out.println("appid="+appid);
        //System.out.println("secret="+secret);
        String url = "";
        try {
           /* Map<String, Object> map2  = WxUtil.oppen_id(request, request.getSession());
            String oppen_id = (String) map2.get("oppen_id");
            String accessToken  = (String) map2.get("access_token");*/
            //1、获取accessToken
            String accessToken = getAccessToken(appid, secret);
            //2、获取Ticket
            String jsapi_ticket = getTicket(accessToken);
            //3、时间戳和随机字符串
            String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

            System.out.println("accessToken:" + accessToken + "\njsapi_ticket:" + jsapi_ticket + "\n时间戳：" + timestamp + "\n随机字符串：" + noncestr);

            //4、获取url
            //String url="http://shuiqitong.com/xzw/jNotice/jNotice_templet/templet.jsp";
            /*根据JSSDK上面的规则进行计算
            String[] ArrTmp = {"jsapi_ticket","timestamp","nonce","url"};
            Arrays.sort(ArrTmp);
            StringBuffer sf = new StringBuffer();
            for(int i=0;i<ArrTmp.length;i++){
                sf.append(ArrTmp[i]);
            }
            */

            //5、将参数排序并拼接字符串
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;

            //6、将字符串进行sha1加密
            String signature = SHA1(str);
//            Map<String,String> map=new HashMap();
//            map.put("timestamp",timestamp);
//            map.put("accessToken",accessToken);
//            map.put("ticket",ticket);
//            map.put("noncestr",noncestr);
//            map.put("signature",signature);
            System.out.println("参数：" + str + "\n签名：" + signature);
            List l_data = new ArrayList();
            l_data.add(timestamp);
            l_data.add(noncestr);
            l_data.add(signature);
            l_data.add(url);
            l_data.add(appid);
            JSONArray l_jsonarrary = JSONArray.fromObject(l_data);
            //json转的字符串值
            String l_jsonstring = l_jsonarrary.toString();
            System.out.println(l_jsonstring);
//            response.getWriter().print(l_jsonstring);
//            response.getWriter().flush();
//            response.getWriter().close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //获取access_token
    public static String getAccessToken(String appid, String secret) {
        String access_token = "";
        String grant_type = "client_credential";//获取access_token填写client_credential
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret;
        //这个url链接地址和参数皆不能变
//        String requestUrl = "";
//        String oppid="";
//        JSONObject oppidObj =null;
//        String openid ="";
//        String requestUrl2="";
//        String userInfoStr="";
//        JSONObject wxUserInfo=null;
        try {
            //获取code后，请求以下链接获取access_token
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println("JSON字符串：" + demoJson);
            access_token = demoJson.getString("access_token");
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }

    //获取ticket
    public static String getTicket(String access_token) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println("JSON字符串：" + demoJson);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}

