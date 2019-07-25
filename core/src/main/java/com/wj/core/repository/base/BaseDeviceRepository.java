package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseDeviceRepository extends JpaRepository<BaseDevice, Integer> {

    /**
     * 根据key查询所属家庭ID
     * @param key
     * @return BaseDevice
     */
    @Query(value = "select * from base_device where device_key = ?1", nativeQuery = true)
    public BaseDevice findByKey(String key);

    /**
     * 根据家庭ID查询所属设备
     * @param fid
     * @returne List<BaseDevice>
     */
    @Query(value = "select * from base_device where family_id = ?1", nativeQuery = true)
    public List<BaseDevice> findByFamilyId(Integer fid);

    /**
     * 分页列表
     * @param flag
     * @returne Page<BaseDevice>
     */
    @Query(value = "select * from base_device where flag = ?1 and status = ?2", nativeQuery = true)
    public Page<BaseDevice> findAll(Integer flag, Integer status, Pageable pageable);

    @Query(value = "select count(*) from base_device where device_key = ?1", nativeQuery = true)
    public Integer findCountByKey(String key);

    @Modifying
    @Query(value = "update BaseDevice b set b.versionCode = ?1, b.versionName = ?2 where b.deviceKey = ?3")
    public void updateVer(String versionCode, String versionName, String key);

    void deleteByFamilyId(Integer familyId);

}
