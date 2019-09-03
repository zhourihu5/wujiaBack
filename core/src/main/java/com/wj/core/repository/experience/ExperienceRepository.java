package com.wj.core.repository.experience;

import com.wj.core.entity.experience.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> , JpaSpecificationExecutor<Experience> {

    @Query(value = "select * from ebiz_experience where status = ?1", nativeQuery = true)
    public Page<Experience> findAllByStatus(String status, Pageable pageable);

    @Modifying
    @Query("update Experience e set e.isShow = ?1, e.updateDate = ?2 where e.id = ?3")
    public void updateExperienceIsShow(String isShow, Date date, Integer id);

    @Query(value = "select * from ebiz_experience where id = ?1", nativeQuery = true)
    public Experience getById(Integer id);
}
