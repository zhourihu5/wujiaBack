package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseFloorRepository extends JpaRepository<BaseFloor, Integer> {


    @Query(value = "select * from base_floor where name like CONCAT('%',?1,'%')", nativeQuery = true)
    public Page<BaseFloor> findByName(String name, Pageable pageable);


}
