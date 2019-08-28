package com.wj.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wj.api.filter.ResponseMessage;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.user.dto.XcxLoginDTO;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.service.activity.ActivityService;
import com.wj.core.service.upload.OssUploadService;
import com.wj.core.service.wx.WxLoginService;
import com.wj.core.service.wx.WxQrCodeService;
import com.wj.core.util.HttpClients;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/activity", tags = "活动接口模块")
@RestController
@RequestMapping("/test/")
public class TestController {

    public final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    OssUploadService ossUploadService;
    @Value("${wj.oss.access}")
    private String ossUrl;
    @Autowired
    private WxQrCodeService wxQrCodeService;

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @ApiOperation(value = "生成小程序二维码", notes = "生成小程序二维码")
    @GetMapping("/activity/generateQrCode")
    public ResponseMessage<String> generateQrCodeMini(Integer activityId) throws Exception {
        String path="images/wxapp/qrcode/orderConfirm";
        String fileName="activity_"+activityId+".png";

        String scene=activityId+"";
        String page="pages/orderConfirm/index";


        String result= wxQrCodeService.testWxappQrCode(path, fileName, scene, page);
        return ResponseMessage.ok(result);

    }
    @ApiOperation(value = "生成小程序二维码", notes = "生成小程序二维码")
    @GetMapping("/order/generateQrCode")
    public ResponseMessage<String> orderQrCode(Integer id) throws Exception {
        String path="images/wxapp/qrcode/orderDetail";
        String fileName="order_"+id+".png";

        String scene=id +"";
        String page="pages/orderDetail/index";

        String result= wxQrCodeService.generateWxappQrCode(path, fileName, scene, page);
        return ResponseMessage.ok(result);

    }

    @GetMapping("/deleteQrCode")
    public void deleteQrCodeMini() throws Exception {
        deleteActivityQrCode();
        deleteOrderQrCode();

    }

    private void deleteActivityQrCode() {
        int pageNum=0;
        int pageSize=20;

        String path="images/wxapp/qrcode/orderConfirm";
        Pageable page = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<Activity> acList = activityRepository.findAll(page);
        while (acList.hasNext()){
            for(Activity ac:acList){
                String fileName="activity_"+ac.getId()+".png";
                ossUploadService.delete(path,fileName);
            }
            pageNum++;
            page = PageRequest.of(pageNum , pageSize, Sort.Direction.DESC, "id");
            acList = activityRepository.findAll(page);
        }
    }
    private void deleteOrderQrCode() {
        int pageNum=0;
        int pageSize=20;

        String path="images/wxapp/qrcode/orderDetail";

        Pageable page = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<OrderInfo> acList = orderInfoRepository.findAll(page);
        while (acList.hasNext()){
            for(OrderInfo ac:acList){
                String fileName="order_"+ac.getId()+".png";
                ossUploadService.delete(path,fileName);
            }
            pageNum++;
            page = PageRequest.of(pageNum , pageSize, Sort.Direction.DESC, "id");
            acList = orderInfoRepository.findAll(page);
        }
    }
}