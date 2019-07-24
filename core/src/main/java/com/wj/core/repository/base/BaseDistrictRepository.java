package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseDistrictRepository extends JpaRepository<BaseDistrict, Integer> {

    @Query(value = "select * from base_district where issue_id = ?1", nativeQuery = true)
    public List<BaseDistrict> findByIssueId(Integer issueId);

    @Query(value = "select * from base_district where issue_id = ?1", nativeQuery = true)
    public Page<BaseDistrict> findByIssueId(Integer issueId, Pageable pageable);

    @Query(value = "select count(*) from base_district where issue_id = ?1", nativeQuery = true)
    public Integer findCountByIssueId(Integer issueId);

    @Query(value = "select * from base_district where communtity_id = ?1", nativeQuery = true)
    public List<BaseDistrict> findByCommuntityId(Integer communtityId);

    @Query(value = "select * from base_district where communtity_id = ?1", nativeQuery = true)
    public Page<BaseDistrict> findByCommuntityId(Integer communtityId, Pageable pageable);

    @Query(value = "select count(1) from base_district where communtity_id = ?1", nativeQuery = true)
    public Integer findCountByCommuntityId(Integer communtityId);

    BaseDistrict findByCode(String code);

    List<BaseDistrict> findByCodeLike(String code);
}
