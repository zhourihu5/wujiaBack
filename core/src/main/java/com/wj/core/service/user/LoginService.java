package com.wj.core.service.user;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserInfoRepository userInfoRepository;






}
