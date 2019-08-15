package com.wj.core.service.qst;

import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class QstLoginService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Value("${qst.info.accessToken}")
    private String ADDRESS;
    @Value("${qst.info.userName}")
    private String userName;
    @Value("${qst.info.password}")
    private String password;
    @Value("${qst.info.uuid}")
    private String uuid;

    /**
     * 1.1	获取访问令牌
     *
     * @return Object
     */
    public Object login() {
        String url = ADDRESS + "accesstoken?userName=" + userName + "&password=" + password;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic " + uuid);
        Object object = HttpClients.getObjectClientAndHeaders(url, requestHeaders);
        return object;
    }

//    public static void main(String[] args) {
//        System.out.println("11111");
//        String url = "http://39.97.233.20:9700/api/accesstoken?userName=AdminJXKJ&password=52c297b78d7f27c878e48dcdb7879dfb";
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("Authorization", "Basic Q0U2RTkyQTAtNDdERS00NzRDLTk1ODgtMDg0M0M1QkFDN0VF");
//        Object object = HttpClients.getObjectClientAndHeaders(url, requestHeaders);
//        System.out.println(object);
//    }


}

