package com.wj.core.repository.activity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.user.SysUserFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query(value = "select * from ebiz_activity where status = ?1", nativeQuery = true)
    public List<Activity> findByStatus(String status);
}
