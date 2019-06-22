package com.wj.core.service.user;

import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.entity.user.enums.UserIdentity;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFamilyService {

    @Autowired
    private UserFamilyRepository userFamilyRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    /**
     * 根据用户ID查询所属家庭
     *
     * @param userId
     * @return List<SysUserFamily>
     */
    public List<SysUserFamily> findByUserId(int userId) {
        return userFamilyRepository.findByUserId(userId);
    }

    /**
     * 根据家庭ID查询家庭成员列表
     *
     * @param familyId
     * @return List<SysUserFamily>
     */
    public List<SysUserFamily> findByFamilyId(int familyId) {
        return userFamilyRepository.findByFamilyId(familyId);
    }


    /**
     * 根据家庭ID获取家庭成员列表详情
     *
     * @param familyId
     * @return List<SysUserInfo>
     */
    public List<SysUserInfo> findFamilyToUser(Integer familyId) {
        List<SysUserFamily> userFamilyList = userFamilyRepository.findByFamilyId(familyId);
        List<SysUserInfo> sysUserInfoList = new ArrayList<>();
        for (SysUserFamily sysUserFamily : userFamilyList) {
            SysUserInfo sysUserInfo = userInfoRepository.findByUserId(sysUserFamily.getUserFamily().getUserId());
            sysUserInfo.setIdentity(sysUserFamily.getIdentity());
            sysUserInfoList.add(sysUserInfo);
        }
        return sysUserInfoList;
    }

    /**
     * 根据用户id和家庭id查询家庭信息
     *
     * @param userId
     * @param familyId
     * @return SysUserFamily
     */
    public SysUserFamily findByUidAndFid(Integer userId, Integer familyId) {
        return userFamilyRepository.findByUidAndFid(userId, familyId);
    }

    /**
     * 绑定用户和家庭关系
     *
     * @param userFamily
     * @return void
     */
    @Transactional
    public void addUserAndFamily(SysUserFamily userFamily) {
        if (UserIdentity.Owner.equals(userFamily.getIdentity())) {
            Integer count = userFamilyRepository.findByFamilyIdAndIdentity(userFamily.getUserFamily().getFamilyId(), userFamily.getIdentity());
            if (count > 0)
                throw new ServiceException("不能重复添加户主", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        userFamilyRepository.save(userFamily);
    }

    /**
     * 解绑用户和家庭关系
     *
     * @param userFamily
     * @return void
     */
    @Transactional
    public Integer delUserAndFamily(UserFamily userFamily) {
        return userFamilyRepository.delUserAndFamily(userFamily.getUserId(), userFamily.getFamilyId());
    }

    /**
     * 获取所有业主的用户ID
     * @param identity
     * @return List<SysUserFamily>
     */
    public List<SysUserFamily> findByIdentity(Integer identity) {
        return userFamilyRepository.findByIdentity(identity);
    }


}
