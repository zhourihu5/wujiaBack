package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseFloorRepository;
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

    @Autowired
    private BaseFloorRepository baseFloorRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

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
        Page<BaseUnit> page = null;
        if (floorId != null) {
            page = baseUnitRepository.findByFloorId(floorId, pageable);
        } else {
            page = baseUnitRepository.findAll(pageable);
        }
        for (BaseUnit baseUnit: page) {
            BaseFloor baseFloor = baseFloorRepository.findByFloorId(baseUnit.getFloorId());
            if (baseFloor != null) baseUnit.setFloorName(baseFloor.getName());
            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
            if (communtity != null) baseUnit.setCommuntityName(communtity.getName());
        }
        return page;
    }


    public List<BaseUnit> findByFloorId(Integer floorId) {
        return baseUnitRepository.findByFloorId(floorId);
    }

}
