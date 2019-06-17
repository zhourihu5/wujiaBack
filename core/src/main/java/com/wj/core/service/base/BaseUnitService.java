package com.wj.core.service.base;

import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseUnitService {

    @Autowired
    private BaseUnitRepository baseUnitRepository;

    /**
     * 保存单元信息
     *
     * @param unit
     * @return void
     */
    public void saveUnit(BaseUnit unit) {
        baseUnitRepository.save(unit);
    }


}
