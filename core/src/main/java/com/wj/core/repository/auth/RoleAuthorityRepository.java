package com.wj.core.repository.auth;

import com.wj.core.entity.user.SysAuthority;
import com.wj.core.entity.user.SysRoleAuthority;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RoleAuthorityRepository extends JpaRepository<SysRoleAuthority, Integer> {

    /**
     * 根据角色id查询所属权限
     *
     * @param roleId
     * @return List<SysRoleAuthority>
     */
    @Query(value = "select * from sys_role_authority where role_id = ?1", nativeQuery = true)
    public List<SysRoleAuthority> findByRoleId(Integer roleId);

    /**
     * 保存
     *
     * @param roleId
     * @param authId
     * @return Integer
     */
    @Modifying
    @Query(value = "insert into sys_role_authority(role_id, auth_id) values(?1, ?2)", nativeQuery = true)
    public Integer saveRoleAuthority(Integer roleId, Integer authId);

    /**
     * 删除
     *
     * @param roleId
     * @return Integer
     */
    @Modifying
    @Query(value = "delete from sys_role_authority where role_id = ?1", nativeQuery = true)
    public Integer delRoleAuthority(Integer roleId);

}
