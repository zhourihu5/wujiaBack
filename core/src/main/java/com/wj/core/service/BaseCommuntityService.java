package com.wj.core.service;

import com.wj.core.entity.BaseCommuntity;
import com.wj.core.repository.BaseCommuntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseCommuntityService {
    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    public List<BaseCommuntity> allList() {
        List<BaseCommuntity> list = baseCommuntityRepository.allList();
        return list;
    }

    public List<BaseCommuntity> findByArea(String code) {
        List<BaseCommuntity> list = baseCommuntityRepository.findByArea(code);
        return list;
    }

    public BaseCommuntity findById(int pid) {
        return baseCommuntityRepository.findById(pid);
    }


}
