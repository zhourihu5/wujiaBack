package com.api.controller;

import com.api.entity.Area;
import com.api.service.AreaService;
import com.api.service.HttpClientService;
import com.api.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/weather/")
public class WeatherController {

    @Autowired
    private HttpClientService httpClientService;

    @RequestMapping(value = "ali", method = RequestMethod.GET)
    public @ResponseBody
    Object all() {
        String cityname = "北京";
        String url = "http://v.juhe.cn/weather/index?cityname=" + cityname + "&key=" + CommonUtils.ALIKEY;
        return httpClientService.getObjectClient(url);
    }


}
