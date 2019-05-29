package com.api.service;

import com.api.entity.BaseCommuntity;
import com.api.mapper.BaseCommuntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseCommuntityService {
    @Autowired
    private BaseCommuntityMapper baseCommuntityMapper;

    public List<BaseCommuntity> allList() {
        List<BaseCommuntity> list = baseCommuntityMapper.allList();
        return list;
    }

    public List<BaseCommuntity> findByArea(String code) {
        List<BaseCommuntity> list = baseCommuntityMapper.findByArea(code);
        return list;
    }

    public BaseCommuntity findById(int pid) {
        return baseCommuntityMapper.findById(pid);
    }


}
