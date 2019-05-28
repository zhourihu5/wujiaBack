package com.api.service;

import com.api.entity.BaseUnit;
import com.api.mapper.BaseUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseUnitService {
    @Autowired
    private BaseUnitMapper mapper;

    public List<BaseUnit> allList() {
        List<BaseUnit> list = mapper.allList();
        return list;
    }

    public BaseUnit findByName(String name) {
        return mapper.findByName(name);
    }

    public List<BaseUnit> findById(String pid) {
        List<BaseUnit> list = mapper.findById(pid);
        return list;
    }


}
