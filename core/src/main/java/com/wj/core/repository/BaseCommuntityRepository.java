package com.wj.core.repository;

import com.wj.core.entity.BaseCommuntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BaseCommuntityRepository extends JpaRepository<BaseCommuntity, Integer> {

    public List<BaseCommuntity> findByArea(String code);

    public List<BaseCommuntity> allList();

    public BaseCommuntity findById(int id);



}
