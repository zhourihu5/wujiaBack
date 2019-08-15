package com.wj.admin.controller.base;

import com.wj.admin.filter.ResponseMessage;
import com.wj.admin.utils.JwtUtil;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.dto.BaseAreaDTO;
import com.wj.core.entity.base.dto.ProvinceDTO;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/v1/area", tags = "省市区接口模块")
@RestController
@RequestMapping("/v1/area/")
public class BaseAreaController {

    private final static Logger logger = LoggerFactory.getLogger(BaseAreaController.class);

    @Autowired
    private BaseAreaService baseAreaService;

    @ApiOperation(value = "查询所有区域信息", notes = "获取所有的区域信息json层级结构返回")
    @GetMapping("all")
    @ResponseBody
    public  ResponseMessage<List<ProvinceDTO>> getAll() {
        return ResponseMessage.ok(baseAreaService.getALl());
    }

    @ApiOperation(value = "根据pid查询省市区信息", notes = "根据pid查询省市区信息")
    @GetMapping("findArea")
    public @ResponseBody
    ResponseMessage<List<BaseArea>> findArea(Integer pid) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("根据pid查询省市区信息接口:/v1/area/findArea userId=" + claims.get("userId"));
        if (pid == null) {
            pid = 0;
        }
        List<BaseArea> list = baseAreaService.findByPid(pid);
        return ResponseMessage.ok(list);
    }

    @ApiOperation(value = "省市区三级联动", notes = "省市区三级联动")
    @GetMapping("findProByPid")
    public @ResponseBody
    ResponseMessage findProByPid(Integer pid, HttpServletRequest request) {
        String token = JwtUtil.getJwtToken();
        Claims claims = JwtUtil.parseJwt(token);
        logger.info("省市区三级联动接口:/v1/area/findProByPid userId=" + claims.get("userId"));
        final HttpSession httpSession = request.getSession();
        Object data = httpSession.getAttribute("area");
        Map<String, Object> map = new HashMap<>();
        if (data == null) {
            if (pid == null) {
                pid = 0;
            }
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
            map.put("area", list);
            httpSession.setAttribute("area", list);
        } else {
            map.put("area", data);
        }
        return ResponseMessage.ok(map);
    }

    private List<BaseAreaDTO> getChilds(BaseArea baseArea) {
        List<BaseArea> cityList = baseAreaService.findByPid(baseArea.getId());
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
