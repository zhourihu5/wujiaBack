package com.wj.core.repository.base;

import com.wj.core.entity.base.SysFamilyCommuntity;
import com.wj.core.entity.base.embeddable.FamilyCommuntity;
import com.wj.core.entity.user.SysUserFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FamilyCommuntityRepository extends JpaRepository<SysFamilyCommuntity, Integer> {

    /**
     * 根据家庭id查询指定社区
     * @param familyId
     * @return Integer
     */
    @Query(value = "select communtity_id from sys_family_communtity where family_id = ?1", nativeQuery = true)
    public Integer findByFamilyId(Integer familyId);

    /**
     * 根据社区id查询所有家庭
     * @param communtityId
     * @return Integer
     */
    @Query(value = "select * from sys_family_communtity where communtity_id = ?1", nativeQuery = true)
    public List<SysFamilyCommuntity> findByCommuntityId(Integer communtityId);


    @Modifying
    @Query(value = "insert into sys_family_communtity(communtity_id, family_id) values(?1, ?2)", nativeQuery = true)
    public Integer insert(Integer communtityId, Integer familyId);

}
