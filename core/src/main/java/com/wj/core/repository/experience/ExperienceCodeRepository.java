package com.wj.core.repository.experience;

import com.wj.core.entity.experience.ExperienceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface ExperienceCodeRepository extends JpaRepository<ExperienceCode, Integer> {

    @Modifying
    @Query("update ExperienceCode e set e.userId = ?1, e.userName = ?2, e.updateDate = ?3 where e.id = ?4")
    public void updateExperienceCode(Integer userId, String userName, Date date, Integer id);

}
