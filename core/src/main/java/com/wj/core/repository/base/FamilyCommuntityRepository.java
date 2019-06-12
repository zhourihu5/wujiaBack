package com.wj.core.repository.base;

import com.wj.core.entity.base.SysFamilyCommuntity;
import com.wj.core.entity.user.SysUserFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FamilyCommuntityRepository extends JpaRepository<SysFamilyCommuntity, Integer> {

    /**
     * 根据家庭id查询指定社区
     * @param fid
     * @return Integer
     */
    @Query(value = "select communtity_id from sys_family_communtity where family_id = ?1", nativeQuery = true)
    public Integer findByFamilyId(Integer fid);



}
