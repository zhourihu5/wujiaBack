package com.wj.api.controller.user;

import com.wj.api.utils.CommonUtils;
import com.wj.api.utils.JwtUtil;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.service.HttpClientService;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.service.base.BaseCommuntityService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/weather/")
public class WeatherController {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private BaseCommuntityService baseCommuntityService;
//
//    @RequestMapping(value = "ali", method = RequestMethod.GET)
//    public @ResponseBody Object ali() {
//        String cityname = null;
//        try {
//            String token = JwtUtil.getJwtToken();
//            Claims claims = JwtUtil.parseJwt(token);
//            Object communtityId = claims.get("communtityId");
//            System.out.println(communtityId);
//            BaseCommuntity baseCommuntity = baseCommuntityService.findById((int)communtityId);
//            BaseArea baseArea = baseAreaService.findAreaById(baseCommuntity.getCity());
//            cityname = baseArea.getAreaName();
//            System.out.println(cityname);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String url = "http://v.juhe.cn/weather/index?cityname=" + cityname + "&key=" + CommonUtils.ALIKEY;
//        return httpClientService.getObjectClient(url);
//    }


}
