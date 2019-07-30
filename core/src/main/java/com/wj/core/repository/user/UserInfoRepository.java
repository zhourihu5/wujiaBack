package com.wj.core.repository.user;

import com.wj.core.entity.user.SysUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserInfoRepository extends JpaRepository<SysUserInfo, Integer> {

    @Query(value = "select * from sys_user_info where user_name like CONCAT('%',?1,'%')", nativeQuery = true)
    public Page<SysUserInfo> findByUserName(String userName, Pageable pageable);

    @Query(value = "select * from sys_user_info where nick_name like CONCAT('%',?1,'%')", nativeQuery = true)
    public Page<SysUserInfo> findByNickName(String nickName, Pageable pageable);

    @Query(value = "select * from sys_user_info where user_name like CONCAT('%',?1,'%') and nick_name like CONCAT('%',?2,'%')", nativeQuery = true)
    public Page<SysUserInfo> findByUserNameAndNickName(String userName, String nickName, Pageable pageable);

    /**
     * 根据名字查询用户信息
     *
     * @param name
     * @return SysUserInfo
     */
    @Query(value = "select * from sys_user_info where user_name = ?1", nativeQuery = true)
    public SysUserInfo findByName(String name);

    /**
     * 根据id查询用户信息
     *
     * @param uid
     * @return SysUserInfo
     */
    @Query(value = "select * from sys_user_info where id = ?1", nativeQuery = true)
    public SysUserInfo findByUserId(Integer uid);

    /**
     * 根据用户名和密码查询用户是否存在
     *
     * @param name
     * @param pwd
     * @return SysUserInfo
     */
    @Query(value = "select * from sys_user_info where user_name = ?1 and password = ?2", nativeQuery = true)
    public SysUserInfo findByNameAndPwd(String name, String pwd);

    /**
     * 绑定用户信息
     *
     * @param userName
     * @param wxOpenId
     * @return void
     */
    @Modifying
    @Query(value = "update sys_user_info set wx_cover = ?2, wx_nick_name = ?3, wx_open_id = ?4 where user_name = ?1", nativeQuery = true)
    public void bindingUser(String userName, String cover, String nickName, String wxOpenId);

    /**
     * 查询微信是否已经绑定用户
     *
     * @param openid
     * @return void
     */
    @Query(value = "select * from sys_user_info where wx_open_id = ?1", nativeQuery = true)
    public SysUserInfo findByOpenId(String openid);
}
