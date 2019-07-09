package com.wj.core.repository.auth;

import com.wj.core.entity.user.SysAuthority;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AuthorityRepository extends JpaRepository<SysAuthority, Integer> {


    /**
     * 查询全部权限
     * @param
     * @return SysUserInfo
     */
    @Query(value = "select * from sys_authority where status = 1", nativeQuery = true)
    public List<SysAuthority> findAll();

    /**
     * 根据ID查询权限
     * @param
     * @return SysUserInfo
     */
    @Query(value = "select * from sys_authority where id = ?1", nativeQuery = true)
    public SysAuthority findByAid(Integer id);

}
