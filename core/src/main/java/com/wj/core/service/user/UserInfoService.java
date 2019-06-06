package com.wj.core.service.user;

import com.google.common.collect.Lists;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.UserInfoDTO;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.mapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BaseFamilyRepository familyRepository;

    @Autowired
    private UserFamilyRepository userFamilyRepository;

    /**
     * 根据名字查询用户信息
     *
     * @param name
     * @return SysUserInfo
     */
    public SysUserInfo findByName(String name) {
        return userInfoRepository.findByName(name);
    }

    /**
     * 选择用户并将用户添加到家庭中
     *
     * @param uid
     * @param fid
     * @return void
     */
    @Transactional
    public void save(Integer uid, Integer fid) {
        BaseFamily f = familyRepository.getOne(fid);
        SysUserInfo sysUserInfo = userInfoRepository.getOne(uid);
        List<BaseFamily> familyList = sysUserInfo.getFamilyId();
        familyList.add(f);
        sysUserInfo.setFamilyId(familyList);
        userInfoRepository.save(sysUserInfo);
    }

    /**
     * 获取用户详细信息
     *
     * @param uid
     * @return void
     */
    @Transactional
    public SysUserInfo findUserInfo(Integer uid) {
        return userInfoRepository.findByUserId(uid);
    }

    /**
     * 新增用户信息并关联家庭表
     *
     * @param userName
     * @param fid
     * @return void
     */
    @Transactional
    public Integer saveUserAndFamily(String userName, Integer fid) {
        SysUserInfo userInfo = userInfoRepository.findByName(userName);
        if (null != userInfo) {
            return 1;
        }
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserName(userName);
        sysUserInfo.setCreateDate(new Date());
        sysUserInfo = userInfoRepository.save(sysUserInfo);
        System.out.println(sysUserInfo.getId());
        // 关联家庭
        UserFamily userFamily = new UserFamily();
        userFamily.setFamilyId(fid);
        userFamily.setUserId(sysUserInfo.getId());
        SysUserFamily sysUserFamily = new SysUserFamily();
        sysUserFamily.setUserFamily(userFamily);
        sysUserFamily.setStatus(2);
        sysUserFamily.setIdentity(1);
        userFamilyRepository.save(sysUserFamily);
        return 0;
    }


}
