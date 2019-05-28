package com.api.controller;


import com.api.entity.BaseCommuntity;
import com.api.service.BaseCommuntityService;
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

