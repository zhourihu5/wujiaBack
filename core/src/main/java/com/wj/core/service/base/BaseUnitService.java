package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseStorey;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.repository.base.BaseStoreyRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
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
public class BaseUnitService {

    @Autowired
    private BaseUnitRepository baseUnitRepository;

    @Autowired
    private BaseFloorRepository baseFloorRepository;

    @Autowired
    private BaseStoreyRepository baseStoreyRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    /**
     * 保存单元信息-层
     *
     * @param unit
     * @return void
     */
    @Transactional
    public void saveUnit(BaseUnit unit) {
        BaseFloor bf = baseFloorRepository.getOne(unit.getFloorId());
        if (unit.getId() == null) {
            Integer count = baseUnitRepository.findCountByFloorId(unit.getFloorId());
            unit.setCode(CommunityUtil.genCode(bf.getCode(), ++ count));
            unit.setCreateDate(ClockUtil.currentDate());
            baseUnitRepository.save(unit);
        } else {
            baseUnitRepository.modityStorey(unit.getStorey(), unit.getId());
        }
        //baseStoreyRepository.deleteByUnitId(unit.getId());
        Long existsNum = baseStoreyRepository.countByUnitId(unit.getId());
        Integer storeyNum = unit.getStorey();
        int num = 0;
        if (existsNum > 0) {
            num = existsNum.intValue() + 1;
        } else  {
            num += 1;
        }
        for (int i = num; i <= storeyNum; i++) {
            BaseStorey baseStorey = new BaseStorey();
            baseStorey.setNum(i);
            baseStorey.setFamilyCount(0);
            baseStorey.setName(i + "层");
            baseStorey.setUnitId(unit.getId());
            baseStorey.setCreateDate(new Date());
            baseStorey.setCode(CommunityUtil.genCode(unit.getCode(), i));
            baseStorey.setIssueId(bf.getIssueId());
            baseStorey.setDistrictId(bf.getDistrictId());
            baseStorey.setFloorId(bf.getId());
            baseStoreyRepository.save(baseStorey);
        }
        String commCode = unit.getCode().substring(0, 8);
        BaseCommuntity bc = baseCommuntityRepository.findByCode(commCode);
        if (!StringUtils.contains(bc.getFlag(), "层")) {
            baseCommuntityRepository.modityFlag(bc.getFlag() + "-层", bc.getId());
        }

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
        for (BaseUnit baseUnit : page) {
            BaseFloor baseFloor = baseFloorRepository.findByFloorId(baseUnit.getFloorId());
            if (baseFloor == null)
                throw new ServiceException("楼数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            baseUnit.setFloorName(baseFloor.getName());
            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
            if (communtity == null)
                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            baseUnit.setCommuntityName(communtity.getName());
        }
        return page;
    }


    public List<BaseUnit> findByFloorId(Integer floorId) {
        return baseUnitRepository.findByFloorId(floorId);
    }


    public List<BaseUnit> getUnits(String commCode, String issuCode, String disCode, String floorCode) {
        if (StringUtils.isNotBlank(floorCode)) {
            return  baseUnitRepository.findByCodeLike(floorCode + "%");
        }
        if (StringUtils.isNotBlank(disCode) && StringUtils.isNotBlank(issuCode)) {
            return  baseUnitRepository.findByCodeLike(disCode + "%");
        }
        if (StringUtils.isNotBlank(disCode)) {
            return  baseUnitRepository.findByCodeLike(disCode + "%");
        }
        if (StringUtils.isNotBlank(issuCode)) {
            return  baseUnitRepository.findByCodeLike(issuCode + "%");
        }
        return  baseUnitRepository.findByCodeLike(commCode + "%");
    }
}
