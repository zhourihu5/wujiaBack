package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseStorey;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.base.BaseStoreyRepository;
import com.wj.core.repository.base.FamilyCommuntityRepository;
import com.wj.core.util.base.CommunityUtil;
import com.wj.core.util.time.ClockUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    @Autowired
    private FamilyCommuntityRepository familyCommuntityRepository;

    public Page<BaseStorey> findAll(Integer unitId, Pageable pageable) {
        Page<BaseStorey> page = null;
        if (unitId != null) {
            page = baseStoreyRepository.findByUnitId(unitId, pageable);
        } else {
            page = baseStoreyRepository.findAll(pageable);
        }
        return page;
    }

    public List<BaseStorey> findByUnitId(Integer unitId) {
        return baseStoreyRepository.findByUnitId(unitId);
    }

    /**
     * 保存每层拥有多少住户
     *
     * @param storey
     * @return void
     */
    @Transactional
    public void saveStoreyAndFamily(BaseStorey storey) {
        if (storey.getId() != null) {
            baseStoreyRepository.modityFamilyCount(storey.getFamilyCount(), storey.getId());
            BaseStorey baseStorey = baseStoreyRepository.findByStoreyid(storey.getId());
            String commCode = baseStorey.getCode().substring(0, 8);
            BaseCommuntity bc = baseCommuntityRepository.findByCode(commCode);
            if (!StringUtils.contains(bc.getFlag(), "家")) {
                baseCommuntityRepository.modityFlag(bc.getFlag() + "-家", bc.getId());
            }
            Long existsNum = baseFamilyRepository.countByStoreyId(storey.getId());
            Integer familyNum = storey.getFamilyCount();
            int num = 0;
            if (existsNum > 0) {
                num = familyNum.intValue() + 1;
            } else  {
                num += 1;
            }
            //baseFamilyRepository.deleteByStoreyId(storey.getId());
            for (int i = num; i <= familyNum; i++) {
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
                baseFamily.setCode(CommunityUtil.genCode(baseStorey.getCode(), i));
                baseFamily.setCreateDate(ClockUtil.currentDate());
                BaseFamily newBaseFamily = baseFamilyRepository.save(baseFamily);
                familyCommuntityRepository.insert(bc.getId(), newBaseFamily.getId());
            }
        }
    }

    public List<BaseStorey> getStoreys(String commCode, String issuCode, String disCode, String floorCode, String unitCode) {
        if (StringUtils.isNotBlank(unitCode)) {
            return baseStoreyRepository.findByCodeLike(unitCode + "%");
        }
        return baseStoreyRepository.findByCodeLike(commCode + "%");
    }
}
