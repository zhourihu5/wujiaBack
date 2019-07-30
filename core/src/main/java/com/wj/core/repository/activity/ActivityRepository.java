package com.wj.core.repository.activity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.order.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {

    @Query(value = "select * from ebiz_activity where status = ?1", nativeQuery = true)
    public List<Activity> findByStatus(String status);

    @Modifying
    @Query("update Activity a set a.status = ?1 where a.id = ?2")
    void modityStatus(String status, Integer id);

    @Modifying
    @Query("update Activity a set a.isShow = ?1 where a.id = ?2")
    void modityIsShow(String isShow, Integer id);

    @Query(value = "select * from ebiz_activity where id = ?1", nativeQuery = true)
    public Activity findByActivityId(Integer id);

    @Query(value = "select * from ebiz_activity", nativeQuery = true)
    public Page<Activity> findAll(Pageable pageable);
}
