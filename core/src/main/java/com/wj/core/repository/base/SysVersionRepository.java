package com.wj.core.repository.base;

import com.wj.core.entity.user.SysVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysVersionRepository extends JpaRepository<SysVersion, Integer> {

    SysVersion findFirstByOrderBySysVerDesc();

    Page<SysVersion> findAll(Pageable pageable);
}
