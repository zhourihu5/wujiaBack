package com.wj.core.repository.user;

import com.wj.core.entity.user.SysUserInfo;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserInfoRepository extends JpaRepository<SysUserInfo, Integer> {


    /**
     * 根据名字查询用户信息
     * @param name
     * @return SysUserInfo
     */
    @Query(value = "select * from sys_user_info where user_name = ?1", nativeQuery = true)
    public SysUserInfo findByName(String name);

    /**
     * 根据名字查询用户信息
     * @param uid
     * @return SysUserInfo
     */
    @Query(value = "select * from sys_user_info where id = ?1", nativeQuery = true)
    public SysUserInfo findByUserId(Integer uid);

    /**
     * 添加用户
     * @param
     * @return int
     */
    @Modifying
    @Query(value = "insert into sys_user_info(user_id, sub_user_id, user_name) values(?1,?2,?3)", nativeQuery = true)
    public int insert(Integer userId, Integer subUserId, String userName);


}
