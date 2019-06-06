package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.repository.base.BaseAreaRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAreaService {
    @Autowired
    private BaseAreaRepository areaRepository;

    /**
     * 根据id查询省市区信息
     * @param id
     * @return BaseArea
     */
    public BaseArea findById(Integer id) {
        return areaRepository.findByCityId(id);
    }
}
