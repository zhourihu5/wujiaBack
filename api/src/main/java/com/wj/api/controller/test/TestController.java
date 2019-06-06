package com.wj.api.controller.test;

import com.wj.core.entity.base.*;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.*;
import com.wj.core.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/")
public class TestController {

    private static UserInfoService userInfoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "hello spring boot!";
    }

    @Autowired
    private BaseCommuntityService baseCommuntityService;
    @Autowired
    private BaseFloorService baseFloorService;
    @Autowired
    private BaseUnitService baseUnitService;
    @Autowired
    private BaseFamilyService baseFamilyService;
    @Autowired
    private BaseAreaService baseAreaService;
//    @Autowired
//    private UserInfoService userInfoService;

//    @RequestMapping(value = "ceshi", method = RequestMethod.GET)
//    @ResponseBody
//    public Object ceshi(String code) {
//        // 根据省市区查询出所有社区
//        List<BaseCommuntity> bcList = baseCommuntityService.findByArea(code);
//        System.out.println("bcList"+bcList.size());
//        List<BaseFloor> bfList = new ArrayList<>();
//        for (BaseCommuntity bc : bcList) {
//            // 查询出每个社区有多少栋楼
//            List<BaseFloor> list = baseFloorService.findById(String.valueOf(bc.getId()));
//            bfList.addAll(list);
//        }
//        System.out.println("bfList"+bfList.size());
//        List<BaseUnit> buList = new ArrayList<>();
//        for (BaseFloor bf : bfList) {
//            // 查询出每栋楼有多少个单元
//            List<BaseUnit> list = baseUnitService.findById(String.valueOf(bf.getId()));
//            buList.addAll(list);
//        }
//        System.out.println("buList"+buList.size());
//        List<BaseFamily> bfmList = new ArrayList<>();
//        for (BaseUnit bu : buList) {
//            // 查询出每个单元有多少个房间/家庭
//            List<BaseFamily> list = baseFamilyService.findById(String.valueOf(bu.getId()));
//            bfmList.addAll(list);
//        }
//        System.out.println("bfmList"+bfmList.size());
//        return bfmList;
//    }
//
//    @RequestMapping(value = "ceshi1", method = RequestMethod.GET)
//    @ResponseBody
//    public Object ceshi1(String familyId) {
//        BaseFamily bfm = baseFamilyService.findByName(familyId);
////        BaseUnit bu = baseUnitService.findByName(bfm.getUnitId());
////        BaseFloor bf = baseFloorService.findByName(bu.getFloorId());
////        BaseCommuntity bc = baseCommuntityService.findById(bf.getCommuntityId());
////        return bc;
//        return bfm;
//    }
//
//    private List<BaseArea> list (int id) {
//        List<BaseArea> list = baseAreaService.findAreaByPid(id);
//        return list;
//    }
}
