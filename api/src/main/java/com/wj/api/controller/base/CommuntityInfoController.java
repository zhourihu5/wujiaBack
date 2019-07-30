package com.wj.api.controller.base;

import com.wj.api.filter.ResponseMessage;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.dto.BaseAreaDTO;
import com.wj.core.service.base.BaseAreaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/communtityInfo/")
public class CommuntityInfoController {

    @Autowired
    private BaseAreaService baseAreaService;


}
