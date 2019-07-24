package com.wj.core.service.base;

import com.wj.core.entity.base.*;
import com.wj.core.repository.base.*;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.CommonUtils;
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
public class BaseFloorService {

    @Autowired
    private BaseFloorRepository baseFloorRepository;
    @Autowired
    private BaseUnitRepository baseUnitRepository;
    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;
    @Autowired
    private BaseDistrictRepository baseDistrictRepository;
    @Autowired
    private BaseIssueRepository baseIssueRepository;
    /**
     * 保存楼信息-单元信息
     *
     * @param floor
     * @return void
     */
    @Transactional
    public BaseFloor saveFloor(BaseFloor floor) {
        if (floor.getId() == null) {
            BaseCommuntity bc = baseCommuntityRepository.getOne(floor.getCommuntityId());
            Integer count = baseFloorRepository.findCountByDistrictId(floor.getDistrictId());
            if (floor.getIssueId() != null && floor.getDistrictId() != null){
                BaseDistrict bd = baseDistrictRepository.getOne(floor.getDistrictId());
                floor.setCode(CommunityUtil.genCode(bd.getCode(), ++ count));
            } else if (floor.getIssueId() != null && floor.getDistrictId() == null) {
                BaseIssue bi = baseIssueRepository.getOne(floor.getIssueId());
                floor.setCode(CommunityUtil.genCode(bi.getCode().concat("00"), ++ count));
            } else if (floor.getIssueId() == null && floor.getDistrictId() != null) {
                BaseDistrict bd = baseDistrictRepository.getOne(floor.getDistrictId());
                floor.setCode(CommunityUtil.genCode(bd.getCode().concat("00"), ++ count));
            } else {
                floor.setCode(CommunityUtil.genCode(bc.getCode().concat("0000"), ++ count));
            }
            if (StringUtils.isNotBlank(bc.getFlag())) {
                if (!StringUtils.contains(bc.getFlag(), "-楼-单"))
                    baseCommuntityRepository.modityFlag(bc.getFlag() + "-楼-单", floor.getCommuntityId());
            } else {
                baseCommuntityRepository.modityFlag("楼-单", floor.getCommuntityId());
            }

            floor.setCreateDate(ClockUtil.currentDate());
            floor = baseFloorRepository.save(floor);

        }
//        baseUnitRepository.deleteByUnitFloorId(floor.getId());
        Long existsUnitNum = baseUnitRepository.countByFloorId(floor.getId());
        Integer unitNum = floor.getUnit();
        int num = 0;
        if (existsUnitNum > 0) {
            num = existsUnitNum.intValue() + 1;
        } else  {
            num += 1;
        }
        for (int i = num; i<= unitNum; i ++) {
            BaseUnit bu = new BaseUnit();
            bu.setCode(CommunityUtil.genCode(floor.getCode(), i));
            bu.setUnitNo(i + "单元");
            bu.setStorey(0);
            bu.setCreateDate(ClockUtil.currentDate());
            bu.setFloorId(floor.getId());
            bu.setIssueId(floor.getIssueId());
            bu.setDistrictId(floor.getDistrictId());
            baseUnitRepository.save(bu);
        }

        baseFloorRepository.save(floor);
        return floor;
    }

    /**
     * 获取楼分页信息
     *
     * @param communtityId
     * @return Page<BaseFloor>
     */
    public Page<BaseFloor> findAll(Integer communtityId, Pageable pageable) {
        Page<BaseFloor> page = null;
        if (communtityId != null) {
            page = baseFloorRepository.findByCommuntityId(communtityId, pageable);
        } else {
            page = baseFloorRepository.findAll(pageable);
        }
        for (BaseFloor baseFloor : page) {
            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
            if (communtity == null)
                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            baseFloor.setCommuntityName(communtity.getName());
        }
        return page;
    }

    public List<BaseFloor> findByCommuntityId(Integer communtityId) {
        return baseFloorRepository.findByCommuntityId(communtityId);
    }

    public List<BaseFloor> getFloors(String commCode, String issuCode, String disCode) {
        if (StringUtils.isNotBlank(issuCode) && StringUtils.isNotBlank(disCode)) {
            return baseFloorRepository.findByCodeLike(disCode + "%");
        }
        if (StringUtils.isNotBlank(issuCode)) {
            return baseFloorRepository.findByCodeLike(issuCode + "%");
        }
        if (StringUtils.isNotBlank(disCode)) {
            return baseFloorRepository.findByCodeLike(disCode + "%");
        }
        return baseFloorRepository.findByCodeLike(commCode + "%");
    }
}
