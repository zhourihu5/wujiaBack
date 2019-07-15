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
    @Value("${wj.path.service}")
    private String servicePath;
    private static final String CARD_TYPE = "card";
    private static final String SERVICE_TYPE = "service";
    private static final String ADV_TYPE = "adv";
    private static final String SCREEN = "screen";
    private static final String BANNER = "banner";
    private static final String APK = "apk";
    private static final String CARD_CONTENT = "card_content";
    @Value("${wj.oss.access}")
    private String url;
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
        }else
        if (type.equals(SERVICE_TYPE)) {
            path = ossUploadService.ossUpload(file, servicePath);
        }else
        if (type.equals(ADV_TYPE)) {
            path = ossUploadService.ossUpload(file, "images/adv/icon");
        }else
        if (type.equals(SCREEN)) {
            path = ossUploadService.ossUpload(file, "images/screen/icon");
        }else
        if (type.equals(BANNER)) {
            path = ossUploadService.ossUpload(file, "images/banner/icon");
        }else
        if (type.equals(APK)) {
            path = ossUploadService.ossUpload(file, "apk/package");
        }else
        if (type.equals(CARD_CONTENT)) {
            path = ossUploadService.ossUpload(file, "images/card/content");
            path = url + path;
        }
        return ResponseMessage.ok(path);
    }


    @PostMapping("/uploadMult")
    public ResponseMessage<String> uploadMult(@RequestParam("file") MultipartFile[] files, String type) {
        String path = "";
        if (type.equals(CARD_CONTENT)) {
            for (MultipartFile f : files) {
                path = ossUploadService.ossUpload(f, "images/card/content");
                path = url + path;
                //???fixme??
            }
        }
        return ResponseMessage.ok(path);
    }

}