package com.wj.core.repository;

import com.wj.core.entity.BaseFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BaseFloorRepository extends JpaRepository<BaseFloor, Integer> {

    public BaseFloor findByName(String name);

    public List<BaseFloor> allList();

    public List<BaseFloor> findById(String id);




}
