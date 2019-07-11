package com.wj.core.service.base;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseStorey;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseStoreyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BaseStoreyService {

    @Autowired
    private BaseStoreyRepository baseStoreyRepository;

    public Page<BaseStorey> findAll(Integer unitId, Pageable pageable) {
        Page<BaseStorey> page = null;
        if (unitId != null) {
            page = baseStoreyRepository.findByUnitId(unitId, pageable);
        } else {
            page = baseStoreyRepository.findAll(pageable);
        }
//        for (BaseUnit baseUnit: page) {
//            BaseFloor baseFloor = baseFloorRepository.findByFloorId(baseUnit.getFloorId());
//            if (baseFloor == null)
//                throw new ServiceException("楼数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
//            baseUnit.setFloorName(baseFloor.getName());
//            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
//            if (communtity == null)
//                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
//            baseUnit.setCommuntityName(communtity.getName());
//        }
        return page;
    }

    public List<BaseStorey> findByUnitId(Integer unitId) {
        return baseStoreyRepository.findByUnitId(unitId);
    }

    /**
     * 保存层和户信息
     *
     * @param storey
     * @return void
     */
    @Transactional
    public void saveStorey(BaseStorey storey) {
        storey.setCreateDate(new Date());
        BaseStorey baseStorey = baseStoreyRepository.save(storey);
        for (int i = 1; i <= Integer.valueOf(baseStorey.getFamilyCount()); i++) {
            BaseFamily baseFamily = new BaseFamily();
            StringBuffer sBuffer = new StringBuffer("菜鸟教程官网：");
            sBuffer.append(baseStorey.getNum());
            if (i < 10) {
                sBuffer.append(0);
            }
            sBuffer.append(i);
            baseFamily.setNum(sBuffer.toString());
            baseFamily.setUnitId(baseStorey.getUnitId());
            baseFamily.setStoreyId(baseFamily.getId());
            baseFamily.setCreateDate(new Date());
            baseStoreyRepository.save(baseStorey);
        }
    }

}
