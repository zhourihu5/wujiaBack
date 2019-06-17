package com.wj.core.service.user;

import com.wj.core.entity.base.SysVersion;
import com.wj.core.repository.base.SysVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    @Autowired
    private SysVersionRepository sysVersionRepository;

    public SysVersion getVer() {
        return sysVersionRepository.findFirstBySysVerGreaterThanEqualOrderBySysVerDesc(Short.valueOf("1"));
    }

}
