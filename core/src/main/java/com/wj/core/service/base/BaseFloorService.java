package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BaseFloorService {

    @Autowired
    private BaseFloorRepository baseFloorRepository;

    /**
     * 保存楼信息
     *
     * @param floor
     * @return void
     */
    @Transactional
    public void saveFloor(BaseFloor floor) {
        baseFloorRepository.save(floor);
    }

    /**
     * 获取楼分页信息
     *
     * @param name
     * @return void
     */
    public Page<BaseFloor> findAll(String name, Pageable pageable) {
        if (!CommonUtils.isNull(name)) {
            return baseFloorRepository.findByName(name, pageable);
        } else {
            return baseFloorRepository.findAll(pageable);
        }
//        if (areaCode != null && !CommonUtils.isNull(name)) {
//            return baseCommuntityRepository.findByAreaCodeAndName(areaCode, name, pageable);
//        } else if (areaCode != null && CommonUtils.isNull(name)) {
//            return baseCommuntityRepository.findByAreaCode(areaCode, pageable);
//        } else if (areaCode == null && !CommonUtils.isNull(name)) {
//            return baseCommuntityRepository.findByName(name, pageable);
//        } else {
//            return baseCommuntityRepository.findAll(pageable);
//        }
    }

}
