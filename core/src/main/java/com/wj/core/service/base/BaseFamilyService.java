package com.wj.core.service.base;

import com.wj.core.entity.base.*;
import com.wj.core.repository.base.*;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
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

    @Autowired
    private BaseAreaRepository baseAreaRepository;

    @Autowired
    private FamilyCommuntityRepository familyCommuntityRepository;

    @Autowired
    private UserFamilyRepository userFamilyRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 保存家庭信息
     *
     * @param family
     * @return void
     */
    @Transactional
    public BaseFamily saveFamily(BaseFamily family) {
        family.setCreateDate(new Date());
        BaseFamily baseFamily = baseFamilyRepository.save(family);
        if (baseFamily == null)
            throw new ServiceException("家庭数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        familyCommuntityRepository.insert(family.getCommuntityId(), baseFamily.getId());
        return baseFamily;
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
        communtity.setName(communtity.getName() + floor.getName() + unit.getUnitNo() + family.getNum());
        return communtity;
    }

    /**
     * 根据家庭id查询家庭-单元-楼-社区信息
     *
     * @param fid
     * @return void
     */
    public String findAllByFamilyId(Integer fid) {
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
        BaseArea province = baseAreaRepository.findByCityId(communtity.getProvince());
        if (province == null) throw new ServiceException("省数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        BaseArea city = baseAreaRepository.findByCityId(communtity.getCity());
        if (city == null) throw new ServiceException("市数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        BaseArea area = baseAreaRepository.findByCityId(communtity.getArea());
        if (area == null) throw new ServiceException("区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
        String address = province.getAreaName() + city.getAreaName() + area.getAreaName() + communtity.getName() + floor.getName() + unit.getUnitNo() + family.getNum();
        return address;
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
        for (BaseFamily baseFamily : page) {
            BaseUnit baseUnit = baseUnitRepository.findByUnitId(baseFamily.getUnitId());
            if (baseUnit == null) {
                throw new ServiceException("单元数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            baseFamily.setUnitName(baseUnit.getUnitNo());
            BaseFloor baseFloor = baseFloorRepository.findByFloorId(baseUnit.getFloorId());
            if (baseFloor == null) {
                throw new ServiceException("楼数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            baseFamily.setFloorName(baseFloor.getName());
            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
            if (communtity == null) {
                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            baseFamily.setCommuntityName(communtity.getName());
            Integer userId = userFamilyRepository.getUserId(baseFamily.getId(), 1);
            if (userId != null && userId > 0) {
                baseFamily.setUserInfo(userInfoRepository.findByUserId(userId));
            }
        }
        return page;
    }

    public List<BaseFamily> getFamilyByStoreyCode(String unitCode) {
        return baseFamilyRepository.findByCodeLike(unitCode + "%");
    }

}
