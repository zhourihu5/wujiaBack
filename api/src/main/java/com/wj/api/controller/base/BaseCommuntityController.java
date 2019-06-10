package com.wj.api.controller.base;


import com.wj.core.service.base.BaseCommuntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/communtity/")
public class BaseCommuntityController {
    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @RequestMapping(value = "findById", method = RequestMethod.GET)
    public @ResponseBody Object findById(Integer id) {
        return baseCommuntityService.findById(id);
    }
}

