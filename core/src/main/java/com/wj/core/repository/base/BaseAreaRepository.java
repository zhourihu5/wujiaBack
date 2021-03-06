package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.dto.BaseAreaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseAreaRepository extends JpaRepository<BaseArea, Integer> {

    /**
     * 根据ID查询省市区信息
     * @param id
     * @return BaseDevice
     */
    @Query(value = "select * from base_area where id = ?1", nativeQuery = true)
    public BaseArea findByCityId(Integer id);

    /**
     * 根据ID查询省市区信息
     * @param pid
     * @return BaseDevice
     */
    @Query(value = "select * from base_area where area_parent_id = ?1", nativeQuery = true)
    public List<BaseArea> findByPid(Integer pid);


}
