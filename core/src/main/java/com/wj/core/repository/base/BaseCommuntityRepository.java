package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BaseCommuntityRepository extends JpaRepository<BaseCommuntity, Integer> {


    /**
     * 根据id查询社区信息
     * @param id
     * @return BaseCommuntity
     */
    @Query(value = "select * from base_communtity where id = ?1", nativeQuery = true)
    public BaseCommuntity findByCommuntityId(Integer id);





}
