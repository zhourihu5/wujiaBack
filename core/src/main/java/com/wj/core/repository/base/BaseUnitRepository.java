package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseUnitRepository extends JpaRepository<BaseUnit, Integer> {

    public BaseUnit findByName(String name);

    public List<BaseUnit> allList();

    public List<BaseUnit> findById(String id);



}
