package com.wj.admin.controller.upload;

import com.wj.admin.filter.ResponseMessage;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.service.upload.OssUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "/oss", tags = "oss上传接口模块")
@RestController
@RequestMapping("/oss/")
public class OssController {

    @Autowired
    private OssUploadService ossUploadService;
    /**
     * @param
     * @MethodName ossUpload
     * @Description 文件上传
     * @Auther thz
     * @Date
     * @Since JDK 1.8
     */
    @ApiOperation(value = "上传", notes = "上传")
    @PostMapping("/oss")
    public Object ossUpload(@RequestParam("file") MultipartFile file, String name) {
        String url = ossUploadService.ossUpload(file, "");
        System.out.println(url);
        System.out.println(name);
        return ResponseMessage.ok(url);
//        JSONObject ret = new JSONObject();
//        String fileNames = "";
//        ret.put("success", false);
//        ret.put("msg", "请求失败[PU01]");
//        try {
//            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
//            Iterator<String> iterator = req.getFileNames();
//            while (iterator.hasNext()) {
//                MultipartFile file = req.getFile(iterator.next());
//                fileNames = file.getOriginalFilename();
//                InputStream input = file.getInputStream();
//                // 创建OSSClient实例
//                OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
//                // 上传文件流
//                ossClient.putObject(BUCKETNAME, KEY + fileNames, input);
//                ossClient.shutdown();
//            }
//            ret.put("success", true);
//            ret.put("msg", KEY + fileNames);
//            System.out.println(("图片上传阿里云 name=" + KEY + fileNames));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ret.getString("msg");
    }

}