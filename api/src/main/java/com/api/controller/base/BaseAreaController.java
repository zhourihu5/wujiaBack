package com.api.controller.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.service.base.BaseAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/area/")
public class BaseAreaController {

    @Autowired
    private BaseAreaService baseAreaService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody Object all() {
        List<BaseArea> list = baseAreaService.areaList();
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
        List<BaseArea> list = baseAreaService.findAreaByName(name);
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
        List<BaseArea> list = baseAreaService.findAreaByPid(pid);
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
        BaseArea area = baseAreaService.findAreaById(id);
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
        List<BaseArea> provinceList = baseAreaService.findAreaByPid(pid);
        List<BaseArea> cityList = new ArrayList<BaseArea>();
        List<BaseArea> areaList = new ArrayList<BaseArea>();
        for (BaseArea a: provinceList) {
            // 市
            cityList = baseAreaService.findAreaByPid(a.getId());
            for (BaseArea b: cityList) {
                // 区
                areaList = baseAreaService.findAreaByPid(b.getId());
//                b.setAreaList(areaList);
            }
//            a.setCityList(cityList);
        }

//        Area area = areaService.findProByPid(pid);
//        List<Area> list = areaService.findAreaByPid(String.valueOf(area.getId()));
        return provinceList;
    }
}
