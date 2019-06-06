package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BaseDeviceRepository extends JpaRepository<BaseDevice, Integer> {

    /**
     * 根据key查询所属家庭ID
     * @param key
     * @return BaseDevice
     */
    @Query(value = "select * from base_device where device_key = ?1", nativeQuery = true)
    public BaseDevice findByKey(String key);

}
