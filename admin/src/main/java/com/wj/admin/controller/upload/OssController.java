package com.wj.admin.controller.upload;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.service.upload.OssUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "/oss", tags = "oss上传接口模块")
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssUploadService ossUploadService;
    @Value("${wj.path.card}")
    private String cardPath;
    private static final String CARD_TYPE = "card";

    /**
     * @param
     * @MethodName ossUpload
     * @Description 文件上传
     * @Auther thz
     * @Date
     * @Since JDK 1.8
     */
    @ApiOperation(value = "上传", notes = "上传")
    @PostMapping("/upload")
    public ResponseMessage<String> ossUpload(@RequestParam("file") MultipartFile file, String type) {
        String path = "";
        if (type.equals(CARD_TYPE)) {
            path = ossUploadService.ossUpload(file, cardPath);
        }
        return ResponseMessage.ok(path);
    }

}