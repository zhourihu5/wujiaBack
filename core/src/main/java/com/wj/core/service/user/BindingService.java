package com.wj.core.service.user;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BindingService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    /**
     * 绑定微信用户
     *
     * @param userName
     * @param userName
     * @return void
     */
    @Transactional
    public void bindingUser(String userName, String wxOpenId) {
        SysUserInfo userInfo = userInfoRepository.findByName(userName);
        if (userInfo == null) {
            throw new ServiceException("此账号不是平台账号", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        userInfoRepository.bindingUser(userName, wxOpenId);
    }

    public Integer getWxOpenId(String wxOpenId) {
        return userInfoRepository.getWxOpenId(wxOpenId);
    }


}

