package com.wj.core.service.base;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseStorey;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseFamilyRepository;
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

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

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
        BaseStorey baseStorey = baseStoreyRepository.findByStoreyid(storey.getId());
        for (int i = 1; i <= Integer.valueOf(storey.getFamilyCount()); i++) {
            BaseFamily baseFamily = new BaseFamily();
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(baseStorey.getNum());
            if (i < 10) {
                sBuffer.append(0);
            }
            sBuffer.append(i);
            baseFamily.setNum(sBuffer.toString());
            baseFamily.setUnitId(baseStorey.getUnitId());
            baseFamily.setStoreyId(baseStorey.getId());

            StringBuffer sBuffer1 = new StringBuffer();
            sBuffer1.append(baseStorey.getCode().substring(0, 16));
            System.out.println("---------" + baseStorey.getCode().substring(0, 16));
            Integer count = baseFamilyRepository.findByStoreyId(baseStorey.getId());
            String number = "";
            if (count == null || count == 0) {
                number = "0001";
            } else if (count > 0 && count < 10) {
                number = "000" + (count + 1);
            } else if (count >= 10 && count < 100) {
                number = "00" + (count + 1);
            } else if (count >= 100 && count < 1000) {
                number = "0" + (count + 1);
            } else if (count >= 1000 && count < 10000) {
                number = "" + (count + 1);
            }
            sBuffer1.append(number);
            System.out.println("sBuffer1111++++++++++++++" + sBuffer1);
            baseStorey.setCode(sBuffer.toString());

            baseFamily.setCreateDate(new Date());
            baseStoreyRepository.save(storey);
        }
    }

}
