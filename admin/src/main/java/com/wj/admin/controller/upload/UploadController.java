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
    public Object uploadImg(@RequestParam("file") MultipartFile[] files) throws IOException {
        String filePath = "/Users/thz/images/";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (MultipartFile multipartFile : files) {
            if (multipartFile.isEmpty()) {
                throw new ServiceException("上传失败，请选择文件", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            String fileName = multipartFile.getOriginalFilename();
            file = new File(filePath + fileName);
            multipartFile.transferTo(file);
        }
        return ResponseMessage.ok();
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

}
