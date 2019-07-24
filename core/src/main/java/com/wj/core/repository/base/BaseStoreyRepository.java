package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseStorey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseStoreyRepository extends JpaRepository<BaseStorey, Integer> {

    @Query(value = "select * from base_storey where id = ?1", nativeQuery = true)
    public BaseStorey findByStoreyid(Integer id);

    @Query(value = "select * from base_storey where unit_id = ?1", nativeQuery = true)
    public List<BaseStorey> findByUnitId(Integer unitId);

    @Query(value = "select * from base_storey where unit_id = ?1", nativeQuery = true)
    public Page<BaseStorey> findByUnitId(Integer unitId, Pageable pageable);

    @Query(value = "select count(*) from base_storey where unit_id = ?1", nativeQuery = true)
    public Integer findCountByUnitId(Integer unitId);

    @Modifying
    @Query(value = "update BaseStorey b set b.familyCount = ?1 where b.id = ?2")
    void modityFamilyCount(Integer num, Integer id);

    @Modifying
    @Query(value = "delete from BaseStorey b where b.unitId = ?1")
    void deleteByUnitId(Integer unitId);

    List<BaseStorey> findByCodeLike(String code);

    Long countByUnitId(Integer unidId);
}
