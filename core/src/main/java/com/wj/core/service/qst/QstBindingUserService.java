package com.wj.core.service.qst;

import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class QstBindingUserService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Value("${qst.info.bindingUser}")
    private String ADDRESS;


    //代理/用户注册
    public Object agentregister() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String accessToken = request.getHeader("Authorization");
            Map<String, Object> requestParam = new HashMap<>();
            requestParam.put("DevUserName", "繁华");
            requestParam.put("Mobile", "18310800470");
            String url = ADDRESS + "agentregister";
            Object object = HttpClients.postObjectClientJsonHeaders(url, accessToken, requestParam);
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



}

