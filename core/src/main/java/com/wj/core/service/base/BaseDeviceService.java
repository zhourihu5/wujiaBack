package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.repository.base.BaseAreaRepository;
import com.wj.core.repository.base.BaseDeviceRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 根据家庭ID查询所属设备
     * @param familyId
     * @return List<BaseDevice>
     */
    public List<BaseDevice> findByFamilyId(Integer familyId) {
        return baseDeviceRepository.findByFamilyId(familyId);
    }
}
