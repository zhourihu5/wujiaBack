package com.api.mapper;

import com.api.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserInfoMapper {

    /**
     * 查询全部用户
     * @param
     * @return List<UserInfo>
     */
    public List<UserInfo> userList();

    /**
     * 根据名字查询用户信息
     * @param name
     * @return UserInfo
     */
    public UserInfo findUserByName(String name);

    /**
     * 根据名字查询用户信息
     * @param id
     * @return UserInfo
     */
    public UserInfo findUserById(int id);

    /**
     * 添加用户
     * @param user
     * @return int
     */
    public int insertUser(UserInfo user);

    /**
     * 根据用户id删除用户信息
     * @param id
     * @return int
     */
    public int deleteUser(int id);

    /**
     * 更新用户信息
     * @param user
     * @return int
     */
    public int updateUser(UserInfo user);

}
