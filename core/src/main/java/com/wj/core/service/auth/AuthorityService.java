package com.wj.core.service.auth;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysAuthority;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.repository.auth.AuthorityRepository;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;


    /**
     * 根据名字查询用户信息
     *
     * @param
     * @return SysUserInfo
     */
    public List<SysAuthority> findAll() {
        return authorityRepository.findAll();
    }


}
