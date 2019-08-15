package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.dto.ProvinceDTO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


public interface BaseAreaService {

    /**
     * 根据id查询省市区信息
     *
     * @param id
     * @return BaseArea
     */
    @Cacheable("areaList")
    public BaseArea findById(Integer id);

    /**
     * 根据id查询省市区信息
     *
     * @param id
     * @return List<BaseArea>
     */
    public List<BaseArea> findByPid(Integer id);
    public List<ProvinceDTO> getALl();

}
