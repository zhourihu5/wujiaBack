package com.wj.api.controller.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.upload.OssUploadService;
import com.wj.core.service.wx.WxLoginService;
import com.wj.core.util.HttpClients;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/activity", tags = "活动接口模块")
@RestController
@RequestMapping("/v1/activity/")
public class ActivityController {

    public final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private WxLoginService wxLoginService;
    @Autowired
    OssUploadService ossUploadService;
    @Value("${wj.oss.access}")
    private String ossUrl;
    @ApiOperation(value = "生成小程序二维码", notes = "生成小程序二维码")
    @GetMapping("/generateQrCode")
    public ResponseMessage<String> generateQrCodeMini(Integer activityId) throws Exception {
        String appid="wxb3a657fc1d81b5d9";//todo 由于我们的小程序还没有发布，我这里用了一个已发布的应用的
        String secret="7197dc021b7ab2b8a934c69db45ea686";//todo 由于我们的小程序还没有发布，我这里用了一个已发布的应用的
        String accessUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appid+
                "&secret=" + secret
                ;
        Object object = HttpClients.getObjectClient(accessUrl);



        JSONObject json = JSON.parseObject(object.toString());
        String accessToken = json.getString("access_token");
        String url="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("scene",activityId);
//        paramMap.put("page","pages/orderConfirm/index");
        paramMap.put("page","pages/index/index");

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(new Gson().toJson(paramMap), headers);
        ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST,
                requestEntity, byte[].class, new Object[0]);
        byte[] result = entity.getBody();
        InputStream inputStream = new ByteArrayInputStream(result);
        JSONObject jsonResult = JSON.parseObject(new String(result));
        if(jsonResult.getInteger("errcode")==0){
            String path="images/wxapp/qrcode/orderConfirm";
            String fileName="activity_"+activityId+".png";
            String imgUrl= ossUploadService.ossUpload(path,fileName,inputStream);
            return ResponseMessage.ok(ossUrl+imgUrl);
        }else {
            logger.error("getwxacodeunlimit :{}",jsonResult);
            throw new RuntimeException("获取小程序二维码失败");
        }




//        String fileName="activity_"+activityId+".txt";
//        File targetFile = new File(fileName);
//        FileOutputStream out = new FileOutputStream(targetFile);
//
//        byte[] buffer = new byte[8192];
//        int bytesRead = 0;
//        while((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//            out.write(buffer, 0, bytesRead);
//        }
//
//        out.flush();
//        out.close();
//        return ResponseMessage.ok(targetFile.getAbsolutePath());


    }

    /**
     * 没参与过的活动列表
     *
     * @param
     * @return ResponseMessage
     * @author thz
     */
    @ApiOperation(value = "没参与过的活动列表", notes = "没参与过的活动列表")
    @GetMapping("findOtherList")
    public ResponseMessage findOtherList() {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        List<Activity> list = activityService.findOtherList(userId);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "活动分页列表", notes = "活动分页列表")
    @GetMapping("/findAll")
    public ResponseMessage<Page<Activity>> findAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "start_date");
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        Page<Activity> page = activityService.findAll(userId, pageable);
        return ResponseMessage.ok(page);
    }

    @ApiOperation(value = "活动全部列表", notes = "活动全部列表")
    @GetMapping("/findList")
    public ResponseMessage<List<Activity>> findList() {
        List<Activity> list = activityService.findList();
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "活动详情", notes = "活动详情")
    @GetMapping("/findByActivityId")
    public ResponseMessage<ActivityUserDTO> findByActivityId(Integer activityId) {
        return ResponseMessage.ok(activityService.findByActivityId(activityId));
    }

    @ApiOperation(value = "确认订单", notes = "确认订单")
    @GetMapping("/isOrder")
    public ResponseMessage<Activity> isOrder(Integer activityId) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        Integer userId = (Integer) claims.get("userId");
        return ResponseMessage.ok(activityService.isOrder(activityId, userId));
    }

}
