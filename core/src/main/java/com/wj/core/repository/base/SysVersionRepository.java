package com.wj.core.repository.base;

import com.wj.core.entity.base.SysVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysVersionRepository extends JpaRepository<SysVersion, Integer> {

    SysVersion findFirstBySysVerGreaterThanEqualOrderBySysVerDesc(Short ver);

}
