package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseFloorRepository extends JpaRepository<BaseFloor, Integer> {


    @Query(value = "select * from base_floor where name like CONCAT('%',?1,'%')", nativeQuery = true)
    public Page<BaseFloor> findByName(String name, Pageable pageable);

    /**
     * 根据楼ID查询楼信息
     *
     * @param floorId
     * @return BaseFloor
     */
    @Query(value = "select * from base_floor where id = ?1", nativeQuery = true)
    public BaseFloor findByFloorId(Integer floorId);
}
