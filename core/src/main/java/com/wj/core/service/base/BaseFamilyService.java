package com.wj.core.service.base;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.repository.base.BaseFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseFamilyService {
    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

//    public List<BaseFamily> allList() {
//        List<BaseFamily> list = baseFamilyRepository.allList();
//        return list;
//    }
//
//    public BaseFamily findById(int id) {
//        return baseFamilyRepository.findById(id);
//    }
//
//    public List<BaseFamily> findById(String pid) {
//        List<BaseFamily> list = baseFamilyRepository.findById(pid);
//        return list;
//    }
    public BaseFamily findByFamilyId(Integer fid) {
        return baseFamilyRepository.findByFamilyId(fid);
    }


}
