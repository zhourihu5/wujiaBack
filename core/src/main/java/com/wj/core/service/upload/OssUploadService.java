package com.wj.core.service.upload;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.repository.op.BannerRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.number.RandomUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

@Service
public class OssUploadService {


    @Value("${wj.oss.point}")
    private String point;
    @Value("${wj.oss.key}")
    private String key;
    @Value("${wj.oss.secret}")
    private String secret;
    @Value("${wj.oss.bucket}")
    private String bucket;

    /**
     * @param
     * @MethodName ossUpload
     * @Description 图片上传
     * @Auther thz
     * @Date
     * @Since JDK 1.8
     */
    public String ossUpload(MultipartFile file, String path) {
        try {
            if (!file.isEmpty()) {
                String fileNames = RandomUtil.randomStringFixLength(6) + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                InputStream input = file.getInputStream();
                return ossUpload(path, fileNames, input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String ossUpload(String path, String fileNames, InputStream input) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(point, key, secret);
        // 上传文件流
        ossClient.putObject(bucket, path + "/" + fileNames, input);
        ossClient.shutdown();
        return path + "/" + fileNames;
    }

    public boolean exist(String path, String fileNames) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(point, key, secret);
        return ossClient.doesObjectExist(bucket, path + "/" + fileNames);
    }
    public void delete(String path, String fileNames){
        OSSClient ossClient = new OSSClient(point, key, secret);
         ossClient.deleteObject(bucket, path + "/" + fileNames);
    }
}
