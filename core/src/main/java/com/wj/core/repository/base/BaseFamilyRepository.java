package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * 根据单元ID查询住户家庭信息
     *
     * @param unitId
     * @return Page<BaseFamily>
     */
    @Query(value = "select * from base_family where unit_id = ?1", nativeQuery = true)
    public Page<BaseFamily> findByUnitId(Integer unitId, Pageable pageable);


    @Query(value = "select count(*) from base_family where storey_id = ?1", nativeQuery = true)
    public Integer findByStoreyId(Integer storeyId);

    @Modifying
    @Query(value = "delete from BaseFamily b where b.storeyId = ?1")
    void deleteByStoreyId(Integer storeyId);

    List<BaseFamily> findByCodeLike(String code);

    Long countByStoreyId(Integer storeyId);
}
