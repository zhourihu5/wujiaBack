package com.wj.api.controller.base;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.dto.BaseAreaDTO;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.util.mapper.BeanMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/area/")
public class BaseAreaController {

    @Autowired
    private BaseAreaService baseAreaService;

    @ApiOperation(value = "根据pid查询省市区信息", notes = "根据pid查询省市区信息")
    @GetMapping("findArea")
    public @ResponseBody ResponseMessage<List<BaseArea>> findArea(Integer pid) {
        List<BaseArea> list = baseAreaService.findByPid(pid);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "省市区三级联动", notes = "省市区三级联动")
    @GetMapping("findProByPid")
    public @ResponseBody
    ResponseMessage<List<BaseAreaDTO>> findProByPid(Integer pid) {
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
