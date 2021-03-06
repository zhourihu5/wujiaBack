package com.wj.core.repository.user;

import com.wj.core.entity.user.SysRole;
import com.wj.core.entity.user.SysScreen;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.models.auth.In;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<SysRole, Integer> {

    @Query(value = "select * from sys_role where name = ?1", nativeQuery = true)
    public SysUserInfo findByName(String name);

    @Modifying
    @Query(value = "insert into sys_role(name) values(?1)", nativeQuery = true)
    public Integer saveRole(String name);

    @Modifying
    @Query(value = "delete from sys_role where id = ?1", nativeQuery = true)
    public Integer delRole(Integer id);
}
