package com.wj.core.service.wx;

import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxLoginService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Value("${wj.wx.appid}")
    private String appid;
    @Value("${wj.wx.secret}")
    private String secret;

    /**
     * 小程序登录
     *
     * @param code
     * @return Object
     */
    public Object wxLogin(String code) {
        System.out.println(appid);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        Object object = HttpClients.getObjectClient(url);
        return object;
    }


}

