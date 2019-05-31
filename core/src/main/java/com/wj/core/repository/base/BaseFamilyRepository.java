package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseFamily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseFamilyRepository extends JpaRepository<BaseFamily, Integer> {

    public BaseFamily findByName(String name);

    public List<BaseFamily> allList();

    public List<BaseFamily> findById(String id);





}
