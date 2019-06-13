package com.wj.core.repository.auth;

import com.wj.core.entity.user.SysRoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleAuthorityRepository extends JpaRepository<SysRoleAuthority, Integer> {



}
