package com.wj.core.service.upload;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.repository.op.BannerRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

@Service
public class OssUploadService {


    public static String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private static String ACCESSKEYID = "LTAIBcmvGzaNrYGd";
    private static String ACCESSKEYSECRET = "0PWTcwRaXzlb7XWG9IflqCc9Y6xmfA";
    private static String BUCKETNAME = "wujia01";
    private static String KEY = "images/";

    /**
     * @param
     * @MethodName ossUpload
     * @Description 图片上传
     * @Auther thz
     * @Date
     * @Since JDK 1.8
     */
    public String ossUpload(MultipartFile file) {
        JSONObject ret = new JSONObject();
        ret.put("msg", "请求失败");
        try {
            String fileNames = file.getOriginalFilename();
            InputStream input = file.getInputStream();
            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
            // 上传文件流
            ossClient.putObject(BUCKETNAME, KEY + fileNames, input);
            ossClient.shutdown();
            ret.put("msg", KEY + fileNames);
            System.out.println(("图片上传阿里云 name=" + KEY + fileNames));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret.getString("msg");
    }


}
