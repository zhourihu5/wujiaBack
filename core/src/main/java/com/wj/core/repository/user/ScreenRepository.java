package com.wj.core.repository.user;

import com.wj.core.entity.user.SysScreen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScreenRepository extends JpaRepository<SysScreen, Integer> {


    /**
     * 根据社区id查询屏保
     * @param communtityId
     * @return SysScreen
     */
    @Query(value = "select * from sys_screen where communtity_id = ?1", nativeQuery = true)
    public SysScreen findById(int communtityId);

    /**
     * 根据id查询屏保
     * @param id
     * @return SysScreen
     */
    @Query(value = "select * from sys_screen where id = ?1", nativeQuery = true)
    public SysScreen findByCommuntityId(int id);

    /**
     * 查询屏保
     * @param
     * @return SysScreen
     */
    @Query(value = "select * from sys_screen", nativeQuery = true)
    public SysScreen find();
//
//    /**
//     * 根据名字查询用户信息
//     * @param id
//     * @return UserInfo
//     */
//    public SysUserInfo findUserById(int id);
//
//    /**
//     * 添加用户
//     * @param user
//     * @return int
//     */
//    public int insertUser(SysUserInfo user);
//
//    /**
//     * 根据用户id删除用户信息
//     * @param id
//     * @return int
//     */
//    public int deleteUser(int id);
//
//    /**
//     * 更新用户信息
//     * @param user
//     * @return int
//     */
//    public int updateUser(SysUserInfo user);

}
