package com.wj.core.service.auth;

import com.wj.core.entity.user.SysRoleAuthority;
import com.wj.core.entity.user.embeddable.RoleAuthority;
import com.wj.core.repository.auth.RoleAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleAuthorityService {

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;


    /**
     * 新增/修改角色路由
     *
     * @param roleAuthority
     * @return void
     */
    @Transactional
    public void saveRoleAuthority(RoleAuthority roleAuthority) {
        SysRoleAuthority sysRoleAuthority = new SysRoleAuthority();
        sysRoleAuthority.setRoleAuthority(roleAuthority);
        roleAuthorityRepository.save(sysRoleAuthority);
    }

    /**
     * 删除角色路由
     *
     * @param roleAuthority
     * @return void
     */
    @Transactional
    public void delRoleAuthority(RoleAuthority roleAuthority) {
        SysRoleAuthority sysRoleAuthority = new SysRoleAuthority();
        sysRoleAuthority.setRoleAuthority(roleAuthority);
        roleAuthorityRepository.delete(sysRoleAuthority);
    }

}
