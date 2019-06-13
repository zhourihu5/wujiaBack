package com.wj.core.service.user;

import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.SysUserRole;
import com.wj.core.entity.user.embeddable.UserRole;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.repository.user.UserRoleRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 根据角色id查询用户列表
     *
     * @param userId
     * @return Integer
     */
    public List<SysUserRole> findByRoleId(Integer userId) {
        return userRoleRepository.findByRoleId(userId);
    }

    /**
     * 根据用户id和对应的角色
     *
     * @param userId
     * @return Integer
     */
    public Integer findByUserId(Integer userId) {
        return userRoleRepository.findByUserId(userId);
    }

    /**
     * 新增用户和角色关系
     *
     * @param userRole
     * @return Integer
     */
    @Transactional
    public void saveUserRole(UserRole userRole) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserRole(userRole);
        userRoleRepository.saveAndFlush(sysUserRole);
    }

    /**
     * 删除用户和角色关系
     *
     * @param userRole
     * @return Integer
     */
    @Transactional
    public void delUserRole(UserRole userRole) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserRole(userRole);
        userRoleRepository.delete(sysUserRole);
    }

}
