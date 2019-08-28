package com.wj.core.service.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.upload.OssUploadService;
import com.wj.core.util.HttpClients;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class WxQrCodeService {

    @Value("${wj.wx.appid}")
    private String appid;
    @Value("${wj.wx.secret}")
    private String secret;
    @Autowired
    OssUploadService ossUploadService;
    @Value("${wj.oss.access}")
    private String ossUrl;


    public String generateWxappQrCode(String path, String fileName, String scene, String page) throws Exception{
        if(ossUploadService.exist(path,fileName)){
            String imgUrl=  path + "/" + fileName;
            return ossUrl+imgUrl;
        }

        String accessUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appid+
                "&secret=" + secret
                ;
        Object object = HttpClients.getObjectClient(accessUrl);

        log.info("appid:{},secret:{}",appid,secret);


        JSONObject json = JSON.parseObject(object.toString());
        String accessToken = json.getString("access_token");


        String url="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("scene",scene);
        paramMap.put("page",page);
        paramMap.put("is_hyaline",true);

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(new Gson().toJson(paramMap), headers);
        ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST,
                requestEntity, byte[].class, new Object[0]);
        byte[] result = entity.getBody();
        InputStream inputStream = new ByteArrayInputStream(result);
        JSONObject jsonResult = null;
        try {
            jsonResult = JSON.parseObject(new String(result));
        } catch (Exception e) {
//            e.printStackTrace();
            Map map=new HashMap();
            map.put("errcode",0);
            jsonResult =new JSONObject(map);
        }
        if(jsonResult.getInteger("errcode")==0){

            String imgUrl= ossUploadService.ossUpload(path,fileName,inputStream);
            return ossUrl+imgUrl;
        }else {
            log.error("getwxacodeunlimit :{}",jsonResult);
            throw new ServiceException("获取小程序二维码失败", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String testWxappQrCode(String path, String fileName, String scene, String page) throws Exception{
         appid="wxb3a657fc1d81b5d9";//todo 由于我们的小程序还没有发布，我这里用了一个已发布的应用的
         secret="7197dc021b7ab2b8a934c69db45ea686";//todo 由于我们的小程序还没有发布，我这里用了一个已发布的应用的
         return generateWxappQrCode(path,fileName,scene,page);
    }


}

