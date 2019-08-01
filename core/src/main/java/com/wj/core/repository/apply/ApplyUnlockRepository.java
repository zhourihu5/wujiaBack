package com.wj.core.repository.apply;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.apply.ApplyUnlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyUnlockRepository extends JpaRepository<ApplyUnlock, Integer>, JpaSpecificationExecutor<ApplyUnlock> {

    @Query(value = "select * from ebiz_apply_unlock where status = ?1", nativeQuery = true)
    public List<ApplyUnlock> findByStatus(String status);

    @Modifying
    @Query("update ebiz_apply_unlock set status = ?1 where a.id = ?2")
    public void modityStatus(String status, Integer id);

}
