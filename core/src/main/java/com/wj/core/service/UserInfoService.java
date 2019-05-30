package com.wj.core.service;

import com.wj.core.entity.UserInfo;
import com.wj.core.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    public List<UserInfo> userList() {
        return userInfoRepository.userList();
    }

    public UserInfo findUserByName(String name) {
        return userInfoRepository.findUserByName(name);
    }

    public UserInfo findUserById(int id) {
        return userInfoRepository.findUserById(id);
    }



}
