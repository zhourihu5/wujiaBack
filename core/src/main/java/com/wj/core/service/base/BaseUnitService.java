package com.wj.core.service.base;

import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Transactional
    public void saveUnit(BaseUnit unit) {
        unit.setCreateDate(new Date());
        baseUnitRepository.save(unit);
    }

    /**
     * 获取单元分页信息
     *
     * @param floorId
     * @return Page<BaseUnit>
     */
    public Page<BaseUnit> findAll(Integer floorId, Pageable pageable) {
        if (floorId != null) {
            return baseUnitRepository.findByFloorId(floorId, pageable);
        } else {
            return baseUnitRepository.findAll(pageable);
        }
    }

    public List<BaseUnit> findByFloorId(Integer floorId) {
        return baseUnitRepository.findByFloorId(floorId);
    }

}
