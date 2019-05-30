package com.wj.core.repository;

import com.wj.core.entity.BaseUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BaseUnitRepository extends JpaRepository<BaseUnit, Integer> {

    public BaseUnit findByName(String name);

    public List<BaseUnit> allList();

    public List<BaseUnit> findById(String id);



}
