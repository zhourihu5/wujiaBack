package com.wj.core.repository.experience;

import com.wj.core.entity.activity.CouponCode;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExperienceCodeRepository extends JpaRepository<ExperienceCode, Integer> {

    @Modifying
    @Query("update ExperienceCode e set e.userId = ?1, e.userName = ?2, e.updateDate = ?3 where e.id = ?4")
    public void updateExperienceCode(Integer userId, String userName, Date date, Integer id);

    @Modifying
    @Query("delete from ExperienceCode e where e.experienceId = ?1")
    public void deleteByExperienceId(Integer experienceId);

    @Query(value = "select * from ebiz_experience_code where user_id = ?1 and finish_date > ?2", nativeQuery = true)
    public Page<ExperienceCode> findByUserIdStart(Integer userId, Date date, Pageable pageable);

    @Query(value = "select * from ebiz_experience_code where user_id = ?1 and finish_date < ?2", nativeQuery = true)
    public Page<ExperienceCode> findByUserIdEnd(Integer userId, Date date, Pageable pageable);

    @Query(value = "select * from ebiz_experience_code where experience_id = ?1", nativeQuery = true)
    public List<ExperienceCode> findByExperienceId(Integer experienceId);

    // 领券人列表
    @Query(value = "select * from ebiz_experience_code where experience_id = ?1 and user_id is not null", nativeQuery = true)
    public List<ExperienceCode> findByExperienceIdAndUserId(Integer experienceId);
}
