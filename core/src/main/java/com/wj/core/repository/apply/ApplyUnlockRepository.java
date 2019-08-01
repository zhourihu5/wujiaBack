package com.wj.core.repository.apply;

import com.wj.core.entity.apply.ApplyLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyUnlockRepository extends JpaRepository<ApplyLock, Integer> {

    @Query(value = "select * from ebiz_apply_lock where status = ?1", nativeQuery = true)
    public List<ApplyLock> findByStatus(String status);

    @Modifying
    @Query("update ebiz_apply_lock set status = ?1 where id = ?2")
    public void modityStatus(String status, Integer id);

}
