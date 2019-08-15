package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseIssueRepository extends JpaRepository<BaseIssue, Integer> {

    @Query(value = "select * from base_issue where id = ?1", nativeQuery = true)
    public BaseIssue findByIssueId(Integer issueId);

    @Query(value = "select * from base_issue where communtity_id = ?1", nativeQuery = true)
    public Page<BaseIssue> findByCommuntityId(Integer communtityId, Pageable pageable);

    @Query(value = "select * from base_issue where communtity_id = ?1", nativeQuery = true)
    public List<BaseIssue> findByCommuntityId(Integer communtityId);

    @Query(value = "select count(id) from base_issue where communtity_id = ?1", nativeQuery = true)
    public Integer findCountByCommuntityId(Integer communtityId);

    public BaseIssue findByCode(String code);

    List<BaseIssue> findByCodeLike(String code);

    int countByCodeLike(String code);
}
