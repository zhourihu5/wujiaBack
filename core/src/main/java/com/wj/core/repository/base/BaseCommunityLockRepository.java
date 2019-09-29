package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseCommuntityLock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BaseCommunityLockRepository extends JpaRepository<BaseCommuntityLock, Integer> {


    /**
     * 根据id查询社区信息
     * @param id
     * @return BaseCommuntity
     */
    @Query(value = "select * from base_community_lock where community_id = ?1", nativeQuery = true)
    public List<BaseCommuntityLock> findByCommuntityId(Integer id);

}
