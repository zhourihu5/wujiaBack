package com.wj.core.service.base;

import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseUnitService {
    @Autowired
    private BaseUnitRepository baseUnitRepository;

    public List<BaseUnit> allList() {
        List<BaseUnit> list = baseUnitRepository.allList();
        return list;
    }

    public BaseUnit findByName(String name) {
        return baseUnitRepository.findByName(name);
    }

    public List<BaseUnit> findById(String pid) {
        List<BaseUnit> list = baseUnitRepository.findById(pid);
        return list;
    }


}
