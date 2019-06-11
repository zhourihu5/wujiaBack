package com.wj.admin.controller.base;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.dto.BaseAreaDTO;
import com.wj.core.service.base.BaseAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "/v1/area", tags = "省市区接口模块")
@RestController
@RequestMapping("/area/")
public class BaseAreaController {

    @Autowired
    private BaseAreaService baseAreaService;

    @ApiOperation(value = "根据pid查询省市区信息", notes = "根据pid查询省市区信息")
    @GetMapping("findArea")
    public @ResponseBody Object findArea(Integer pid) {
        List<BaseArea> list = baseAreaService.findByPid(pid);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "省市区三级联动", notes = "省市区三级联动")
    @GetMapping("findProByPid")
    public @ResponseBody
    Object findProByPid(Integer pid) {
        // 省
        List<BaseArea> provinceList = baseAreaService.findByPid(pid);
        List<BaseAreaDTO> list = new ArrayList<>();
        for (BaseArea province : provinceList) {
            BaseAreaDTO baseAreaDTO = new BaseAreaDTO();
            baseAreaDTO.setId(province.getId());
            baseAreaDTO.setAreaName(province.getAreaName());
            baseAreaDTO.setAreaCode(province.getAreaCode());
            baseAreaDTO.setAreaParentId(province.getAreaParentId());
            List<BaseAreaDTO> pList = getChilds(province);
            baseAreaDTO.setList(pList);
            list.add(baseAreaDTO);
        }
        return ResponseMessage.ok(list);
    }

    private List<BaseAreaDTO> getChilds(BaseArea baseArea) {
        List<BaseArea> cityList =  baseAreaService.findByPid(baseArea.getId());
        List<BaseAreaDTO> list = new ArrayList<>();
        for (BaseArea city : cityList) {
            BaseAreaDTO baseAreaDTO = new BaseAreaDTO();
            baseAreaDTO.setId(city.getId());
            baseAreaDTO.setAreaName(city.getAreaName());
            baseAreaDTO.setAreaCode(city.getAreaCode());
            baseAreaDTO.setAreaParentId(city.getAreaParentId());
            List<BaseAreaDTO> pList = getChilds(city);
            baseAreaDTO.setList(pList);
            list.add(baseAreaDTO);
        }
        return list;
    }
}
