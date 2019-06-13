package com.wj.admin.controller;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.dto.BaseAreaDTO;
import com.wj.core.service.base.BaseAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * @see @Order注解的执行优先级是按value值从小到大顺序。
 * @author pangps
 * @version v1.0
 */
@Component
@Order(value=1)
public class MyApplicationRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Autowired
    private BaseAreaService baseAreaService;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("==服务启动后，初始化数据操作==");
//        Integer pid = 0;
//        // 省
//        List<BaseArea> provinceList = baseAreaService.findByPid(pid);
//        List<BaseAreaDTO> list = new ArrayList<>();
//        for (BaseArea province : provinceList) {
//            BaseAreaDTO baseAreaDTO = new BaseAreaDTO();
//            baseAreaDTO.setId(province.getId());
//            baseAreaDTO.setAreaName(province.getAreaName());
//            baseAreaDTO.setAreaCode(province.getAreaCode());
//            baseAreaDTO.setAreaParentId(province.getAreaParentId());
//            List<BaseAreaDTO> pList = getChilds(province);
//            baseAreaDTO.setList(pList);
//            list.add(baseAreaDTO);
//        }
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        request.getSession().setAttribute("area", list);
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