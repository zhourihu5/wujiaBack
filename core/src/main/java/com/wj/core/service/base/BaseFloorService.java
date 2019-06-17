package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.repository.base.BaseFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void saveFloor(BaseFloor floor) {
        baseFloorRepository.save(floor);
    }


}
