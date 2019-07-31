package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseCommuntityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BaseCommuntityInfoRepository extends JpaRepository<BaseCommuntityInfo, Integer> {

    @Query(value = "select * from base_communtity_info", nativeQuery = true)
    public List<BaseCommuntityInfo> findList();

    @Query(value = "select * from base_communtity_info", nativeQuery = true)
    public Page<BaseCommuntityInfo> findAll(Pageable pageable);

}
