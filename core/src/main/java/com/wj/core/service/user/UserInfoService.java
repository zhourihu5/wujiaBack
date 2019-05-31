package com.wj.core.service.user;

import com.wj.core.entity.user.UserInfo;
import com.wj.core.repository.user.UserInfoRepository;
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
