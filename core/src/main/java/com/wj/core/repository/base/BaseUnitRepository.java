package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * 根据楼ID查询单元信息
     *
     * @param floorId
     * @return BaseUnit
     */
    @Query(value = "select * from base_unit where floor_id = ?1", nativeQuery = true)
    public Page<BaseUnit> findByFloorId(Integer floorId, Pageable pageable);

    @Query(value = "select * from base_unit where floor_id = ?1", nativeQuery = true)
    public List<BaseUnit> findByFloorId(Integer floorId);

    @Query(value = "select count(*) from base_unit where floor_id = ?1", nativeQuery = true)
    public Integer findCountByFloorId(Integer floorId);

    @Query(value = "select * from base_unit where code like CONCAT('%',?1,'%')", nativeQuery = true)
    public List<BaseUnit> findByCode(String code);

    @Modifying
    @Query(value = "update BaseUnit b set b.storey = ?1 where b.id = ?2")
    void modityStorey(Integer num, Integer id);

    List<BaseUnit> findByCodeLike(String code);

    @Modifying
    @Query(value = "delete from BaseUnit b where b.floorId = ?1")
    void deleteByUnitFloorId(Integer floorId);

    Long countByFloorId(Integer floorId);

    int countByCodeLike(String code);

    @Query(value = "select * from base_unit where code = ?1", nativeQuery = true)
    public BaseUnit findByUnitCode(String code);
}
