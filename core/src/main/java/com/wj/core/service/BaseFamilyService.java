package com.wj.core.service;

import com.wj.core.entity.BaseFamily;
import com.wj.core.repository.BaseFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseFamilyService {
    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    public List<BaseFamily> allList() {
        List<BaseFamily> list = baseFamilyRepository.allList();
        return list;
    }

    public BaseFamily findByName(String name) {
        return baseFamilyRepository.findByName(name);
    }

    public List<BaseFamily> findById(String pid) {
        List<BaseFamily> list = baseFamilyRepository.findById(pid);
        return list;
    }


}
