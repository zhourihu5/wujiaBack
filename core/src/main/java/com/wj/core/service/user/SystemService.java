package com.wj.core.service.user;

import com.wj.core.entity.user.SysVersion;
import com.wj.core.repository.base.SysVersionRepository;
import com.wj.core.util.jiguang.JPush;
import com.wj.core.util.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SystemService {

    @Autowired
    private SysVersionRepository sysVersionRepository;

    public SysVersion getVer() {
        SysVersion v = sysVersionRepository.findFirstByOrderBySysVerDesc();
//        JsonMapper mapper = JsonMapper.defaultMapper();
//        JPush.sendPushAll("SYS", mapper.toJson(v));
        return v;
    }

    @Transactional
    public void save(SysVersion version) {
        sysVersionRepository.save(version);
        SysVersion v = getVer();
        JsonMapper mapper = JsonMapper.defaultMapper();
        JPush.sendPushAll("SYS", mapper.toJson(v));
    }

}
