package com.wj.core.repository.user;

import com.wj.core.entity.user.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<SysUserInfo, Integer> {

//    /**
//     * 查询全部用户
//     * @param
//     * @return List<UserInfo>
//     */
//    public List<SysUserInfo> userList();
//
//    /**
//     * 根据名字查询用户信息
//     * @param name
//     * @return UserInfo
//     */
    @Query(value = "select * from sys_user_info where userName = ?1", nativeQuery = true)
    public SysUserInfo findByName(String name);
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
