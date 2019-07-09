package com.wj.core.service.user;

import com.wj.core.entity.user.SysVersion;
import com.wj.core.repository.base.SysVersionRepository;
import com.wj.core.util.jiguang.JPush;
import com.wj.core.util.mapper.JsonMapper;
import com.wj.core.util.time.ClockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SystemService {

    @Autowired
    private SysVersionRepository sysVersionRepository;

    public SysVersion getVer() {
        SysVersion v = sysVersionRepository.findFirstByOrderBySysVerDesc();
        return v;
    }

    public Page<SysVersion> getPage(Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "id");
        return sysVersionRepository.findAll(page);
    }



    @Transactional
    public void save(SysVersion version) {
        if (version != null) {
            version.setCreateDate(ClockUtil.currentDate());
        }
        sysVersionRepository.save(version);
        JsonMapper mapper = JsonMapper.defaultMapper();
        JPush.sendPushAll("SYS", mapper.toJson(version));
    }

}
