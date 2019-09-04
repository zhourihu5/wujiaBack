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
    @Query("update ExperienceCode e set e.userId = ?1, e.userName = ?2, e.updateDate = ?3 where e.experienceCode = ?4")
    public void updateExperienceCodeByCode(Integer userId, String userName, Date date, String experienceCode);

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

    @Query(value = "select * from ebiz_experience_code where experience_id = ?1 and user_id is not null", nativeQuery = true)
    public Page<ExperienceCode> findByExperienceIdAndUserId(Integer experienceId, Pageable pageable);

    // 领券人数量
    @Query(value = "select count(*) from ebiz_experience_code where experience_id = ?1 and user_id is not null", nativeQuery = true)
    public Integer findCountByExperienceIdAndUserId(Integer experienceId);

    // 查询未领取的体验券第一个
    @Query(value = "select * from ebiz_experience_code where experience_id = ?1 and user_id is null order by id desc", nativeQuery = true)
    public List<ExperienceCode> findByUserIdNull(Integer experienceId);

    // 根据用户id和体验券id查询领取张数
    @Query(value = "select count(*) from ebiz_experience_code where experience_id = ?1 and user_id = ?2", nativeQuery = true)
    public Integer findCountByExperienceIdAndUserId(Integer experienceId, Integer userId);
}
