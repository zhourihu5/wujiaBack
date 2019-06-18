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
     * @param communtityId
     * @return Page<BaseFloor>
     */
    public Page<BaseFloor> findAll(Integer communtityId, Pageable pageable) {
        if (communtityId != null) {
            return baseFloorRepository.findByCommuntityId(communtityId, pageable);
        } else {
            return baseFloorRepository.findAll(pageable);
        }
    }

    public List<BaseFloor> findByCommuntityId(Integer communtityId) {
        return baseFloorRepository.findByCommuntityId(communtityId);
    }
}
