package com.wj.admin.controller.auth;

import com.wj.core.service.auth.AuthorityService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/auth", tags = "权限接口模块")
@RestController
@RequestMapping("/auth/")
public class AuthorityController {

    public final static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private AuthorityService authorityService;




}
