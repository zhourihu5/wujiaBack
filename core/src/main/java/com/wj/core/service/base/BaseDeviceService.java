package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.repository.base.BaseAreaRepository;
import com.wj.core.repository.base.BaseDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseDeviceService {
    @Autowired
    private BaseDeviceRepository baseDeviceRepository;

    /**
     * 根据key查询所属家庭ID
     * @param key
     * @return BaseDevice
     */
    public BaseDevice findByKey(String key) {
        return baseDeviceRepository.findByKey(key);
    }
}
