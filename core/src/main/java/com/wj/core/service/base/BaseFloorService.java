package com.wj.core.service.base;

import com.wj.core.entity.base.BaseFloor;
import com.wj.core.repository.base.BaseFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseFloorService {
    @Autowired
    private BaseFloorRepository baseFloorRepository;

//    public List<BaseFloor> allList() {
//        List<BaseFloor> list = baseFloorRepository.allList();
//        return list;
//    }
//
//    public BaseFloor findByName(String name) {
//        return baseFloorRepository.findByName(name);
//    }
//
//    public List<BaseFloor> findById(String pid) {
//        List<BaseFloor> list = baseFloorRepository.findById(pid);
//        return list;
//    }


}
