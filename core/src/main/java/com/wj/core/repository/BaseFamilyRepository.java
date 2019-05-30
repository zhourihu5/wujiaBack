package com.wj.core.repository;

import com.wj.core.entity.BaseFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BaseFamilyRepository extends JpaRepository<BaseFamily, Integer> {

    public BaseFamily findByName(String name);

    public List<BaseFamily> allList();

    public List<BaseFamily> findById(String id);





}
