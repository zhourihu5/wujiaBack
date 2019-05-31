package com.api.controller.base;


import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.service.base.BaseCommuntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/communtity/")
public class BaseCommuntityController {
    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody Object all() {
        List<BaseCommuntity> list = baseCommuntityService.allList();
        return list;
    }
}

