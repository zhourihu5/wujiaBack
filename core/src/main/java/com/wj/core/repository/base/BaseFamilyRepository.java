package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseFamilyRepository extends JpaRepository<BaseFamily, Integer> {

    /**
     * 根据家庭ID查询信息
     * @param fid
     * @return BaseFamily
     */
    @Query(value = "select * from base_family where id = ?1", nativeQuery = true)
    public BaseFamily findByFamilyId(Integer fid);


}