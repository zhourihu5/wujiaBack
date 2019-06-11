package com.wj.admin.controller.base;


import com.wj.core.service.base.BaseCommuntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/v1/communtity", tags = "社区接口模块")
@RestController
@RequestMapping("/communtity/")
public class BaseCommuntityController {
    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @RequestMapping(value = "findById", method = RequestMethod.GET)
    public @ResponseBody Object findById(Integer id) {
        return baseCommuntityService.findById(id);
    }
}

