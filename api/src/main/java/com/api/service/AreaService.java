package com.api.service;

import com.api.entity.Area;
import com.api.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
    @Autowired
    private AreaMapper areaMapper;

    public List<Area> areaList() {
        List<Area> list = areaMapper.areaList();
        return list;
    }

    public List<Area> findAreaByName(String name) {
        List<Area> list = areaMapper.findAreaByName(name);
        return list;
    }

    public List<Area> findAreaByPid(int pid) {
        return areaMapper.findAreaByPid(pid);
    }

    public Area findAreaById(int id) {
        return areaMapper.findAreaById(id);
    }
}
