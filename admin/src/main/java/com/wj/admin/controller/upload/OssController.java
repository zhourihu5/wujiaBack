package com.wj.admin.controller.upload;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Api(value = "/oss", tags = "oss上传接口模块")
@RestController
@RequestMapping("/oss/")
public class OssController {

    public static String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    public static String ACCESSKEYID = "LTAI9rV1x0TmtGjq";
    public static String ACCESSKEYSECRET = "3QCJw4MhlyZC8zQUATKaLxWZpk4bFY";
    public static String BUCKETNAME = "songsiraliyun";
    public static String KEY = "images/";

    /**
     * @param request
     * @MethodName myphotoupload
     * @Description 图片上传
     * @Auther SongYapeng
     * @Date 2019/1/15 9:16
     * @Since JDK 1.8
     */
    @ResponseBody
    @RequestMapping(value = "/photoupload")
    public String myphotoupload(HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        String key = "";
        String fileNames = "";
        ret.put("success", false);
        ret.put("msg", "请求失败[PU01]");
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                fileNames = file.getOriginalFilename();
                InputStream input = file.getInputStream();

                // 创建OSSClient实例
                OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
                // 上传文件流
                ossClient.putObject(BUCKETNAME, KEY + fileNames, input);
                ossClient.shutdown();
            }
            ret.put("success", true);
            ret.put("msg", key + fileNames);
            System.out.println(("图片上传阿里云 name=" + key + fileNames));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret.getString("msg");
    }
}