package com.wj.core.repository.experience;

import com.wj.core.entity.experience.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> , JpaSpecificationExecutor<Experience> {

    @Query(value = "select * from ebiz_experience where status = ?1", nativeQuery = true)
    public Page<Experience> findAllByStatus(String status, Pageable pageable);

}
