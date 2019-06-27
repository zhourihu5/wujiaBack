package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.repository.base.BaseAreaRepository;
import com.wj.core.repository.base.BaseDeviceRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDeviceService {

    @Autowired
    private BaseDeviceRepository baseDeviceRepository;

    /**
     * 根据key查询所属家庭ID
     *
     * @param key
     * @return BaseDevice
     */
    public BaseDevice findByKey(String key) {
        return baseDeviceRepository.findByKey(key);
    }

    /**
     * 根据家庭ID查询所属设备
     *
     * @param familyId
     * @return List<BaseDevice>
     */
    public List<BaseDevice> findByFamilyId(Integer familyId) {
        return baseDeviceRepository.findByFamilyId(familyId);
    }

    /**
     * 保存设备
     *
     * @param device
     * @return void
     */
    public void saveDevice(BaseDevice device) {
        baseDeviceRepository.save(device);
    }

    /**
     * 分页
     *
     * @param flag
     * @return Page<BaseDevice>
     */
    public Page<BaseDevice> findAll(Integer flag, Integer status, Pageable pageable) {
        if (flag == null && status == null) {
            throw new ServiceException("参数异常", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Page<BaseDevice> page = null;
        page = baseDeviceRepository.findAll(flag, status, pageable);
        return page;
    }
}
