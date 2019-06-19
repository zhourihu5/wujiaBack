package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.dto.BaseCommuntityDTO;
import com.wj.core.repository.base.BaseAreaRepository;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BaseCommuntityService {

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    @Autowired
    private BaseAreaRepository baseAreaRepository;

    /**
     * 根据id查询社区信息
     *
     * @param id
     * @return BaseCommuntity
     */
    public BaseCommuntity findById(Integer id) {
        return baseCommuntityRepository.findByCommuntityId(id);
    }

    /**
     * 保存社区信息
     *
     * @param communtity
     * @return void
     */
    @Transactional
    public void saveCommuntity(BaseCommuntity communtity) {
        communtity.setCreateDate(new Date());
        baseCommuntityRepository.save(communtity);
    }

    /**
     * 获取社区分页信息
     *
     * @param areaCode
     * @param name
     * @return Page<BaseCommuntity>
     */
    public Page<BaseCommuntity> findAll(Integer areaCode, String name, Pageable pageable) {
        Page<BaseCommuntity> page = null;
        if (areaCode != null && !CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByAreaCodeAndName(areaCode, name, pageable);
        } else if (areaCode != null && CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByAreaCode(areaCode, pageable);
        } else if (areaCode == null && !CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByName(name, pageable);
        } else {
            page = baseCommuntityRepository.findAll(pageable);
        }
        for (BaseCommuntity baseCommuntity: page) {
            BaseArea area = baseAreaRepository.findByCityId(baseCommuntity.getArea());
            BaseArea city = baseAreaRepository.findByCityId(baseCommuntity.getCity());
            BaseArea provice = baseAreaRepository.findByCityId(baseCommuntity.getProvince());
            if (area != null) baseCommuntity.setAreaName(area.getAreaName());
            if (city != null) baseCommuntity.setCityName(city.getAreaName());
            if (provice != null) baseCommuntity.setProvinceName(provice.getAreaName());

        }
        return page;
    }

    /**
     * 根据市code查询当前所有社区
     *
     * @param areaCode
     * @return List<BaseCommuntity>
     */
    public List<BaseCommuntity> findByAreaCode(Integer areaCode) {
        return baseCommuntityRepository.findByAreaCode(areaCode);
    }

}
