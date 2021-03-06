package com.wj.core.repository.user;

import com.wj.core.entity.user.SysUserFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserFamilyRepository extends JpaRepository<SysUserFamily, Integer> {

    /**
     * 根据用户id查询家庭列表
     * @param userId
     * @return List<SysUserFamily>
     */
    @Query(value = "select * from sys_user_family where user_id = ?1", nativeQuery = true)
    public List<SysUserFamily> findByUserId(Integer userId);

    /**
     * 根据家庭id查询家庭列表
     * @param familyId
     * @return List<SysUserFamily>
     */
    @Query(value = "select * from sys_user_family where family_id = ?1", nativeQuery = true)
    public List<SysUserFamily> findByFamilyId(Integer familyId);

    /**
     * 根据用户id和家庭id查询家庭信息
     * @param userId
     * @param familyId
     * @return SysUserFamily
     */
    @Query(value = "select * from sys_user_family where user_id = ?1 and family_id = ?2", nativeQuery = true)
    public SysUserFamily findByUidAndFid(Integer userId, Integer familyId);

    /**
     * 根据用户id和家庭id查询家庭信息
     * @param userId
     * @param familyId
     * @return SysUserFamily
     */
    @Modifying
    @Query(value = "delete from sys_user_family where user_id = ?1 and family_id = ?2", nativeQuery = true)
    public Integer delUserAndFamily(Integer userId, Integer familyId);

    /**
     * 根据家庭id查询家庭列表
     * @param familyId
     * @return List<SysUserFamily>
     */
    @Query(value = "select count(*) from sys_user_family where family_id = ?1 and identity = ?2", nativeQuery = true)
    public Integer findByFamilyIdAndIdentity(Integer familyId, Integer identity);

    /**
     * 获取所有业主的用户ID
     * @param identity
     * @return List<SysUserFamily>
     */
    @Query(value = "select * from sys_user_family where identity = ?1", nativeQuery = true)
    public List<SysUserFamily> findByIdentity(Integer identity);

    /**
     * 根据家庭id查询业主id
     * @param familyId
     * @return List<SysUserFamily>
     */
    @Query(value = "select user_id from sys_user_family where family_id = ?1 and identity = ?2", nativeQuery = true)
    public Integer getUserId(Integer familyId, Integer identity);

    void deleteByUserFamily_FamilyIdAndIdentity(Integer familyId, Integer identity);

    @Modifying
    @Query(value = "delete from sys_user_family where family_id = ?1", nativeQuery = true)
    public void deleteByFamilyId(Integer familyId);

    @Query(value = "select * from sys_user_family where family_id = ?1 and identity = ?2", nativeQuery = true)
    public List<SysUserFamily> findListByFamilyIdAndIdentity(Integer familyId, Integer identity);

}
