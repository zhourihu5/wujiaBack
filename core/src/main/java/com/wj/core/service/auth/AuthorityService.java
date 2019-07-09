package com.wj.core.service.auth;

import com.wj.core.entity.user.SysAuthority;
import com.wj.core.entity.user.SysRoleAuthority;
import com.wj.core.repository.auth.AuthorityRepository;
import com.wj.core.repository.auth.RoleAuthorityRepository;
import com.wj.core.repository.user.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 根据名字查询用户信息
     *
     * @param
     * @return SysUserInfo
     */
    public List<SysAuthority> findAll() {
        return authorityRepository.findAll();
    }

    /**
     * 根据id查询路由信息
     *
     * @param id
     * @return SysAuthority
     */
    public SysAuthority findById(Integer id) {
        return authorityRepository.findByAid(id);
    }


    /**
     * 新增/修改角色
     *
     * @param sysAuthority
     * @return void
     */
    @Transactional
    public void saveAuthority(SysAuthority sysAuthority) {
        authorityRepository.save(sysAuthority);
    }

    /**
     * 删除角色
     *
     * @param sysAuthority
     * @return void
     */
    @Transactional
    public void delAuthority(SysAuthority sysAuthority) {
        authorityRepository.delete(sysAuthority);
    }

    /**
     * 根据用户ID查询账号所属权限
     *
     * @param userId
     * @return SysAuthority
     */
    public List<SysAuthority> getAuthByUserId(Integer userId) {
        List<SysAuthority> sysAuthorityList = new ArrayList<>();
        // 根据uid查询用户role
        Integer roleId = userRoleRepository.findByUserId(userId);
        // 根据role查询authority
        List<SysRoleAuthority> roleAuthorityList = roleAuthorityRepository.findByRoleId(roleId);
        roleAuthorityList.forEach(SysRoleAuthority -> {
            SysAuthority sysAuthority = authorityRepository.findByAid(SysRoleAuthority.getRoleAuthority().getAuthorityId());
            sysAuthorityList.add(sysAuthority);
        });
        return sysAuthorityList;
    }

}
