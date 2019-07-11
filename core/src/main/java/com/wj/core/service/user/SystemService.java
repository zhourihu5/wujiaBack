package com.wj.core.service.user;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.dto.DeviceVersionDTO;
import com.wj.core.entity.user.SysVersion;
import com.wj.core.repository.base.BaseDeviceRepository;
import com.wj.core.repository.base.SysVersionRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.collection.ArrayUtil;
import com.wj.core.util.jiguang.JPush;
import com.wj.core.util.mapper.JsonMapper;
import com.wj.core.util.time.ClockUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class SystemService {

    @Autowired
    private SysVersionRepository sysVersionRepository;
    @Autowired
    private BaseDeviceRepository baseDeviceRepository;

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
    public void updateVer(DeviceVersionDTO d) {
        BaseDevice b = baseDeviceRepository.findByKey(d.getKey());
        if (b == null) {
            throw new ServiceException("PAD SN 不存在", ErrorCode.DEVICE_KEY_NOTEXSTS);
        }
        baseDeviceRepository.updateVer(d.getVersionCode(), d.getVersionName(), d.getKey());
    }



    @Transactional
    public void save(SysVersion version) {
        if (version != null) {
            version.setCreateDate(ClockUtil.currentDate());
        }
        sysVersionRepository.save(version);
        if (StringUtils.isNotBlank(version.getCommuntityId())) {
            List<String> tags = Arrays.asList(version.getCommuntityId().split(","));
            JsonMapper mapper = JsonMapper.defaultMapper();
            JPush.sendPushAll(tags, "SYS", mapper.toJson(version));
        } else {
            JsonMapper mapper = JsonMapper.defaultMapper();
            JPush.sendPushAll("SYS", mapper.toJson(version));
        }
    }

}
