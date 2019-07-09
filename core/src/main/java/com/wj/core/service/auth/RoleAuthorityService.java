package com.wj.core.service.auth;

import com.wj.core.entity.user.SysRoleAuthority;
import com.wj.core.entity.user.dto.RoleAuthorityDTO;
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
     * @param roleAuthorityDTO
     * @return void
     */
    @Transactional
    public void saveRoleAuthority(RoleAuthorityDTO roleAuthorityDTO) {
        // 先删除
        roleAuthorityRepository.delRoleAuthority(roleAuthorityDTO.getRoleId());
        for (int i = 0; i < roleAuthorityDTO.getAuthId().length; i++) {
            // 在新增
            roleAuthorityRepository.saveRoleAuthority(roleAuthorityDTO.getRoleId(), roleAuthorityDTO.getAuthId()[i]);
        }
//        for (int i = 0; i < roleAuthorityDTO.getAuthId().length; i++) {
//            SysRoleAuthority sysRoleAuthority = new SysRoleAuthority();
//            RoleAuthority roleAuthority = new RoleAuthority();
//            roleAuthority.setRoleId(roleAuthorityDTO.getRoleId());
//            roleAuthority.setAuthorityId(roleAuthorityDTO.getAuthId()[i]);
//            sysRoleAuthority.setRoleAuthority(roleAuthority);
//            roleAuthorityRepository.save(sysRoleAuthority);
//        }
    }


    /**
     * 根据角色id查询所属权限
     *
     * @param roleId
     * @return List<SysRoleAuthority>
     */
    public List<SysRoleAuthority> findByRoleId(Integer roleId) {
        return roleAuthorityRepository.findByRoleId(roleId);
    }
}
