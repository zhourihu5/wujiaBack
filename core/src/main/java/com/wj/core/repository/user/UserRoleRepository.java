package com.wj.core.repository.user;

import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserRole;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRoleRepository extends JpaRepository<SysUserRole, Integer> {

    /**
     * 根据角色id查询用户列表
     * @param roleId
     * @return List<SysUserFamily>
     */
    @Query(value = "select * from sys_user_role where role_id = ?1", nativeQuery = true)
    public List<SysUserRole> findByRoleId(Integer roleId);


    /**
     * 根据用户id和对应的角色
     * @param userId
     * @return Integer
     */
    @Query(value = "select role_id from sys_user_role where user_id = ?1", nativeQuery = true)
    public Integer findByUserId(Integer userId);

    @Modifying
    @Query(value = "insert into sys_user_role(user_id, role_id) values(?1, ?2)", nativeQuery = true)
    public Integer saveUserRole(Integer userId, Integer roleId);

    @Modifying
    @Query(value = "delete from sys_user_role where user_id = ?1, role_id = ?2", nativeQuery = true)
    public Integer delUserRole(Integer userId, Integer roleId);

}
