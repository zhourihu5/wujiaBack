package com.api.controller;

import com.api.entity.Area;
import com.api.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/area/")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody Object all() {
        List<Area> list = areaService.areaList();
        return list;
    }

    @RequestMapping(value = "findAreaByName", method = RequestMethod.GET)
    public @ResponseBody Object findAreaByName(String name) {
        List<Area> list = areaService.findAreaByName(name);
        return list;
    }

    @RequestMapping(value = "findAreaByPid", method = RequestMethod.GET)
    public @ResponseBody Object findAreaByPid(String pid) {
        List<Area> list = areaService.findAreaByPid(pid);
        return list;
    }

    @RequestMapping(value = "findCityByPid", method = RequestMethod.GET)
    public @ResponseBody Object findCityByPid(String pid) {
        List<Area> list = areaService.findAreaByPid(pid);
        return list;
    }

    @RequestMapping(value = "findProByPid", method = RequestMethod.GET)
    public @ResponseBody Object findProByPid(String pid) {
        // 省
        List<Area> provinceList = areaService.findAreaByPid(pid);
        List<Area> cityList = new ArrayList<Area>();
        List<Area> areaList = new ArrayList<Area>();
        for (Area a: provinceList) {
            // 市
            cityList = areaService.findAreaByPid(String.valueOf(a.getId()));
            for (Area b: cityList) {
                // 区
                areaList = areaService.findAreaByPid(String.valueOf(b.getId()));
                b.setAreaList(areaList);
            }
            a.setCityList(cityList);
        }

//        Area area = areaService.findProByPid(pid);
//        List<Area> list = areaService.findAreaByPid(String.valueOf(area.getId()));
        return provinceList;
    }
}
