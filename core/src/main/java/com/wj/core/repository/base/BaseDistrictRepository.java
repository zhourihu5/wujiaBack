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

}
