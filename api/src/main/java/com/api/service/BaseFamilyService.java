package com.api.service;

import com.api.entity.BaseFamily;
import com.api.mapper.BaseFamilyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseFamilyService {
    @Autowired
    private BaseFamilyMapper mapper;

    public List<BaseFamily> allList() {
        List<BaseFamily> list = mapper.allList();
        return list;
    }

    public BaseFamily findByName(String name) {
        return mapper.findByName(name);
    }

    public List<BaseFamily> findById(String pid) {
        List<BaseFamily> list = mapper.findById(pid);
        return list;
    }


}
