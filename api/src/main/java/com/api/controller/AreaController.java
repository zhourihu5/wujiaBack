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

    /**
     * 根据name查询省市区信息
     *
     * @param name
     * @return Object
     * @author thz
     */
    @RequestMapping(value = "findAreaByName", method = RequestMethod.GET)
    public @ResponseBody Object findAreaByName(String name) {
        List<Area> list = areaService.findAreaByName(name);
        return list;
    }

    /**
     * 根据pid查询省市区信息
     *
     * @param pid
     * @return Object
     * @author thz
     */
    @RequestMapping(value = "findAreaByPid", method = RequestMethod.GET)
    public @ResponseBody Object findAreaByPid(int pid) {
        List<Area> list = areaService.findAreaByPid(pid);
        return list;
    }

    /**
     * 根据id查询省市区信息
     *
     * @param id
     * @return Object
     * @author thz
     */
    @RequestMapping(value = "findAreaById", method = RequestMethod.GET)
    public @ResponseBody Object findAreaById(int id) {
        Area area = areaService.findAreaById(id);
        return area;
    }


    /**
     * 省市区三级联动
     *
     * @param pid
     * @return Object
     * @author thz
     */
    @RequestMapping(value = "findProByPid", method = RequestMethod.GET)
    public @ResponseBody Object findProByPid(int pid) {
        // 省
        List<Area> provinceList = areaService.findAreaByPid(pid);
        List<Area> cityList = new ArrayList<Area>();
        List<Area> areaList = new ArrayList<Area>();
        for (Area a: provinceList) {
            // 市
            cityList = areaService.findAreaByPid(a.getId());
            for (Area b: cityList) {
                // 区
                areaList = areaService.findAreaByPid(b.getId());
                b.setAreaList(areaList);
            }
            a.setCityList(cityList);
        }

//        Area area = areaService.findProByPid(pid);
//        List<Area> list = areaService.findAreaByPid(String.valueOf(area.getId()));
        return provinceList;
    }
}
