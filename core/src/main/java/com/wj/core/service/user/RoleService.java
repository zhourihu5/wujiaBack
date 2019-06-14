package com.wj.core.service.user;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysRole;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.RoleRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.CommonUtils;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 根据名字查询角色信息
     *
     * @param name
     * @return SysUserInfo
     */
    public SysUserInfo findByName(String name) {
        return roleRepository.findByName(name);
    }


    public List<SysRole> findAll() {
        return roleRepository.findAll();
    }

    /**
     * 新增/修改角色
     *
     * @param sysRole
     * @return void
     */
    @Transactional
    public void saveRole(SysRole sysRole) {
        roleRepository.save(sysRole);
    }

    /**
     * 删除角色
     *
     * @param sysRole
     * @return void
     */
    @Transactional
    public void delRole(SysRole sysRole) {
        roleRepository.delete(sysRole);
    }

}

