package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.repository.base.BaseAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAreaService {
    @Autowired
    private BaseAreaRepository areaRepository;

//    public List<BaseArea> areaList() {
//        List<BaseArea> list = areaRepository.areaList();
//        return list;
//    }
//
//    public List<BaseArea> findAreaByName(String name) {
//        List<BaseArea> list = areaRepository.findAreaByName(name);
//        return list;
//    }
//
//    public List<BaseArea> findAreaByPid(int pid) {
//        return areaRepository.findAreaByPid(pid);
//    }
//
//    public BaseArea findAreaById(int id) {
//        return areaRepository.findAreaById(id);
//    }
}
