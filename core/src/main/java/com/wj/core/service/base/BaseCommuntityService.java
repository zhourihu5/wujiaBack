package com.wj.core.service.base;

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
        baseCommuntityRepository.save(communtity);
    }

    /**
     * 获取社区分页信息
     *
     * @param areaCode
     * @param name
     * @return void
     */
    public Page<BaseCommuntity> findAll(Integer areaCode, String name, Pageable pageable) {
        if (areaCode != null && !CommonUtils.isNull(name)) {
            return baseCommuntityRepository.findByAreaCodeAndName(areaCode, name, pageable);
        } else if (areaCode != null && CommonUtils.isNull(name)) {
            return baseCommuntityRepository.findByAreaCode(areaCode, pageable);
        } else if (areaCode == null && !CommonUtils.isNull(name)) {
            return baseCommuntityRepository.findByName(name, pageable);
        } else {
            Page<BaseCommuntity> page = baseCommuntityRepository.findAll(pageable);
            for (BaseCommuntity baseCommuntity: page) {
                BaseCommuntityDTO baseCommuntityDTO = new BaseCommuntityDTO();
//            baseAreaRepository.findByCityId(baseCommuntity.getId()).getAreaName();
                baseCommuntityDTO.setAreaName("aaa");
            }
            return baseCommuntityRepository.findAll(pageable);
        }
    }

}
