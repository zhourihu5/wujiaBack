package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseFloor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseFloorRepository extends JpaRepository<BaseFloor, Integer> {


    @Query(value = "select * from base_floor where name like CONCAT('%',?1,'%')", nativeQuery = true)
    public Page<BaseFloor> findByName(String name, Pageable pageable);

    /**
     * 根据楼ID查询楼信息
     *
     * @param floorId
     * @return BaseFloor
     */
    @Query(value = "select * from base_floor where id = ?1", nativeQuery = true)
    public BaseFloor findByFloorId(Integer floorId);

    @Query(value = "select * from base_floor where communtity_id = ?1", nativeQuery = true)
    public Page<BaseFloor> findByCommuntityId(Integer communtityId, Pageable pageable);

    @Query(value = "select * from base_floor where communtity_id = ?1", nativeQuery = true)
    public List<BaseFloor> findByCommuntityId(Integer communtityId);

    @Query(value = "select count(*) from base_floor where communtity_id = ?1", nativeQuery = true)
    public Integer findCountByCommuntityId(Integer communtityId);

    @Query(value = "select count(*) from base_floor where issue_id = ?1", nativeQuery = true)
    public Integer findCountByIssueId(Integer getIssueId);

    @Query(value = "select count(*) from base_floor where district_id = ?1", nativeQuery = true)
    public Integer findCountByDistrictId(Integer districtId);
}
