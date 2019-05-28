package com.api.service;

import com.api.entity.BaseFloor;
import com.api.mapper.BaseFloorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseFloorService {
    @Autowired
    private BaseFloorMapper mapper;

    public List<BaseFloor> allList() {
        List<BaseFloor> list = mapper.allList();
        return list;
    }

    public BaseFloor findByName(String name) {
        return mapper.findByName(name);
    }

    public List<BaseFloor> findById(String pid) {
        List<BaseFloor> list = mapper.findById(pid);
        return list;
    }


}
