package com.wj.api.controller.qst;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.qst.QstCommuntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(value = "/qst/communtity", tags = "")
@RestController
@RequestMapping("/qst/communtity/")
public class QstCommuntityController {

    @Autowired
    private QstCommuntityService qstCommuntityService;


    @ApiOperation(value = "创建小区", notes = "创建小区")
    @PostMapping("tenantvillages")
    public ResponseMessage tenantvillages() {
        return ResponseMessage.ok(qstCommuntityService.tenantvillages());
    }


    @ApiOperation(value = "配置小区位长信息", notes = "配置小区位长信息")
    @PostMapping("TenantStructureDefinition")
    public ResponseMessage TenantStructureDefinition() {
        return ResponseMessage.ok(qstCommuntityService.TenantStructureDefinition());
    }

    @ApiOperation(value = "添加小区节点", notes = "添加小区节点")
    @PostMapping("tenantstructures")
    public ResponseMessage tenantstructures() {
        return ResponseMessage.ok(qstCommuntityService.tenantstructures());
    }


    @ApiOperation(value = "添加楼栋（单元门）", notes = "添加楼栋（单元门）")
    @PostMapping("tenantunitdoors")
    public ResponseMessage tenantunitdoors() {
        return ResponseMessage.ok(qstCommuntityService.tenantunitdoors());
    }




//    @ApiOperation(value = "快速构建小区", notes = "快速构建小区")
//    @PostMapping("tenantstructures")
//    public ResponseMessage tenantstructures() {
//        //获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        //从获取RequestAttributes中获取HttpServletRequest的信息
//        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        try {
//            String accessToken = request.getHeader("Authorization");
//            Map<String, Object> requestParam0 = new HashMap<>();
//            requestParam0.put("AreaID", "110000000000");
//            requestParam0.put("VillageName", "ceshi1");
//            requestParam0.put("PeriodDisplay", "期");
//            requestParam0.put("PeriodNum", 2);
//            requestParam0.put("PeriodNumStart", 1);
//            requestParam0.put("RegionDisplay", "区");
//            requestParam0.put("RegionNum", 2);
//            requestParam0.put("RegionNumStart", 1);
//            requestParam0.put("BuildingNum", 3);
//            requestParam0.put("BuildingNumStart", 1);
//            requestParam0.put("UnitNum", 2);
//            requestParam0.put("UnitNumStart", 1);
//            requestParam0.put("FloorNum", 3);
//            requestParam0.put("FloorNumStart", 1);
//            requestParam0.put("RoomNum", 2);
//            requestParam0.put("RoomNumStart", 1);
//            Map<String, Object> requestParam = new HashMap<>();
//            requestParam.put("Pattern", 0);
//            requestParam.put("TenantCode", "T0001");
//            requestParam.put("Structure", requestParam0);
//            return ResponseMessage.ok(qstCommuntityService.tenantstructures(accessToken, requestParam));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
////        return ResponseMessage.ok();
//    }



}
