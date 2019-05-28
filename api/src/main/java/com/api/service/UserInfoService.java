package com.api.service;

import com.api.entity.UserInfo;
import com.api.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    public List<UserInfo> userList() {
        return userInfoMapper.userList();
    }

    public UserInfo findUserByName(String name) {
        return userInfoMapper.findUserByName(name);
    }

    public UserInfo findUserById(int id) {
        return userInfoMapper.findUserById(id);
    }



}
