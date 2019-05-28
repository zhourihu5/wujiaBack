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
        System.out.println("userList--------");
        return list;
    }

    public List<Area> findAreaByName(String name) {
        List<Area> list = areaMapper.findAreaByName(name);
        System.out.println("findAreaByName--------");
        return list;
    }

    public List<Area> findAreaByPid(String pid) {
        List<Area> list = areaMapper.findAreaByPid(pid);
        System.out.println("findAreaByPid--------");
        return list;
    }

    public Area findProByPid(String pid) {
        Area area = areaMapper.findProByPid(pid);
        System.out.println("findProByPid--------");
        return area;
    }
}
