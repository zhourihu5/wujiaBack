package com.wj.admin.controller.upload;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Api(value = "/upload", tags = "上传接口模块")
//@RestController
@RequestMapping("/upload")
@Controller
public class UploadController {
    public final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @ApiOperation(value = "上传", notes = "上传")
    @PostMapping("/upload")
    public Object uploadImg(@RequestParam("file") MultipartFile file) throws IOException {

        return ResponseMessage.ok();
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

}
