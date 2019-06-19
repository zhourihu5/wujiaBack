package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BaseFamilyService {

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    @Autowired
    private BaseUnitRepository baseUnitRepository;

    @Autowired
    private BaseFloorRepository baseFloorRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    /**
     * 保存家庭信息
     *
     * @param family
     * @return void
     */
    @Transactional
    public void saveFamily(BaseFamily family) {
        family.setCreateDate(new Date());
        baseFamilyRepository.save(family);
    }

    /**
     * 根据家庭id查询家庭-单元-楼-社区信息
     *
     * @param fid
     * @return void
     */
    public BaseCommuntity findCommuntityByFamilyId(Integer fid) {
        BaseFamily family = baseFamilyRepository.findByFamilyId(fid);
        if (family == null || family.getUnitId() == null)
            throw new ServiceException("家庭数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        BaseUnit unit = baseUnitRepository.findByUnitId(family.getUnitId());
        if (unit == null || unit.getFloorId() == null)
            throw new ServiceException("单元数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        BaseFloor floor = baseFloorRepository.findByFloorId(unit.getFloorId());
        if (floor == null || floor.getCommuntityId() == null)
            throw new ServiceException("楼数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(floor.getCommuntityId());
        if (communtity == null) throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        communtity.setName(communtity.getName() + floor.getName() + unit.getNum() + family.getNum());
        return communtity;
    }

    /**
     * 获取家庭分页信息
     *
     * @param unitId
     * @return Page<BaseFamily>
     */
    public Page<BaseFamily> findAll(Integer unitId, Pageable pageable) {
        Page<BaseFamily> page = null;
        if (unitId != null) {
            page = baseFamilyRepository.findByUnitId(unitId, pageable);
        } else {
            page = baseFamilyRepository.findAll(pageable);
        }
        for (BaseFamily baseFamily: page) {
            BaseUnit baseUnit = baseUnitRepository.findByUnitId(baseFamily.getUnitId());
            if (baseUnit != null) baseFamily.setUnitName(baseUnit.getNum());
            BaseFloor baseFloor = baseFloorRepository.findByFloorId(baseUnit.getFloorId());
            if (baseFloor != null) baseUnit.setFloorName(baseFloor.getName());
            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
            if (communtity != null) baseUnit.setCommuntityName(communtity.getName());
        }
        return page;
    }

}
