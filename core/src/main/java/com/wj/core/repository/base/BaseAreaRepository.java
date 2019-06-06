package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseAreaRepository extends JpaRepository<BaseArea, Integer> {

//    public List<BaseArea> findAreaByName(String name);
//
//    public List<BaseArea> areaList();
//
//    public List<BaseArea> findAreaByPid(int pid);
//
    /**
     * 根据ID查询省市区信息
     * @param id
     * @return BaseDevice
     */
    @Query(value = "select * from base_area where id = ?1", nativeQuery = true)
    public BaseArea findByCityId(Integer id);


}
