package com.wj.core.service.user;

import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;


//    public List<SysUserInfo> userList() {
//        return userInfoRepository.userList();
//    }
//
    public SysUserInfo findByName(String name) {
        return userInfoRepository.findByName(name);
    }
//
//    public SysUserInfo findUserById(int id) {
//        return userInfoRepository.findUserById(id);
//    }



}
