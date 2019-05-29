package com.api.controller;

import com.api.entity.Area;
import com.api.entity.BaseCommuntity;
import com.api.service.AreaService;
import com.api.service.BaseCommuntityService;
import com.api.service.HttpClientService;
import com.api.utils.CommonUtils;
import com.api.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/weather/")
public class WeatherController {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @RequestMapping(value = "ali", method = RequestMethod.GET)
    public @ResponseBody Object ali() {
        String cityname = null;
        try {
            String token = JwtUtil.getJwtToken();
            Claims claims = JwtUtil.parseJwt(token);
            Object communtityId = claims.get("communtityId");
            System.out.println(communtityId);
            BaseCommuntity baseCommuntity = baseCommuntityService.findById((int)communtityId);
            Area area = areaService.findAreaById(baseCommuntity.getCity());
            cityname = area.getAreaName();
            System.out.println(cityname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "http://v.juhe.cn/weather/index?cityname=" + cityname + "&key=" + CommonUtils.ALIKEY;
        return httpClientService.getObjectClient(url);
    }


}
