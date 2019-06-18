package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseUnitRepository extends JpaRepository<BaseUnit, Integer> {

    /**
     * 根据单元ID查询信息
     *
     * @param unitId
     * @return BaseUnit
     */
    @Query(value = "select * from base_unit where id = ?1", nativeQuery = true)
    public BaseUnit findByUnitId(Integer unitId);

}
