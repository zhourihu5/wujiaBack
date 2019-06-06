package com.wj.api.controller.user;

import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import com.wj.api.utils.HttpUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.service.HttpClientService;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.service.base.BaseCommuntityService;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weather/")
public class WeatherController {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private BaseCommuntityService baseCommuntityService;


    @ApiOperation(value = "阿里24小时天气查询", notes = "阿里24小时天气查询")
    @GetMapping("query")
    public @ResponseBody Object query(Integer communtityId) {
        String json = null;
        try {
            BaseCommuntity baseCommuntity = baseCommuntityService.findById(communtityId);
            if (null == baseCommuntity) {
                return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "数据异常");
            }
            BaseArea baseArea = baseAreaService.findById(baseCommuntity.getCity());
            if (null == baseArea) {
                return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "数据异常");
            }
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + CommonUtils.APPCODE);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("area", baseArea.getAreaName());
            HttpResponse response = HttpUtils.doGet(CommonUtils.HOST, CommonUtils.PATH, CommonUtils.METHOD, headers, querys);
            json = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void main(String[] args) {
        String host = "https://ali-weather.showapi.com";
        String path = "/hour24";
        String method = "GET";
        String appcode = "0ccda9e342a84ffe94da122cf081b1bd";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("area", "北京");
//        querys.put("areaid", "101230506");
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
