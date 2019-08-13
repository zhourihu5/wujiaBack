package com.wj.core.repository.apply;

import com.wj.core.entity.apply.ApplyLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyUnlockRepository extends JpaRepository<ApplyLock, Integer>, JpaSpecificationExecutor<ApplyLock> {

    @Query(value = "select * from ebiz_apply_lock where status = ?1", nativeQuery = true)
    public List<ApplyLock> findByStatus(String status);

    @Query(value = "select * from ebiz_apply_lock where user_id = ?1 order by create_date desc", nativeQuery = true)
    public List<ApplyLock> findByUserId(Integer userId);

    @Query(value = "select * from ebiz_apply_lock where id = ?1", nativeQuery = true)
    public ApplyLock findByApplyId(Integer id);

    @Query(value = "select count(*) from ebiz_apply_lock where user_id = ?1 and family_id = ?2 and status = ?3", nativeQuery = true)
    public Integer findByUserIdAndFamilyId(Integer userId, Integer familyId, String status);

    @Modifying
    @Query(value = "update ebiz_apply_lock set status = ?1 where id = ?2", nativeQuery = true)
    public void updateStatus(String status, Integer id);

    @Modifying
    @Query(value = "update ebiz_apply_lock set status = ?1, remark = ?2 where id = ?3", nativeQuery = true)
    public void updateStatusAndRemark(String status, String remark, Integer id);

}
