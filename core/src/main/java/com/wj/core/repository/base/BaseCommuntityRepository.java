package com.wj.core.repository.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.user.SysUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BaseCommuntityRepository extends JpaRepository<BaseCommuntity, Integer> {


    /**
     * 根据id查询社区信息
     * @param id
     * @return BaseCommuntity
     */
    @Query(value = "select * from base_communtity where id = ?1", nativeQuery = true)
    public BaseCommuntity findByCommuntityId(Integer id);

    @Query(value = "select * from base_communtity where name like CONCAT('%',?1,'%')", nativeQuery = true)
    public Page<BaseCommuntity> findByName(String name, Pageable pageable);

    @Query(value = "select * from base_communtity where area = ?1", nativeQuery = true)
    public Page<BaseCommuntity> findByAreaCode(Integer areaCode, Pageable pageable);

    @Query(value = "select * from base_communtity where area = ?1 and name like CONCAT('%',?2,'%')", nativeQuery = true)
    public Page<BaseCommuntity> findByAreaCodeAndName(Integer areaCode, String nickName, Pageable pageable);

    @Query(value = "select * from base_communtity where city = ?1", nativeQuery = true)
    public Page<BaseCommuntity> findByCityCode(Integer cityCode, Pageable pageable);

    /**
     * 根据市code查询当前所有社区
     * @param areaCode
     * @return List<BaseCommuntity>
     */
    @Query(value = "select * from base_communtity where area = ?1", nativeQuery = true)
    public List<BaseCommuntity> findByAreaCode(Integer areaCode);

    @Query(value = "select * from base_communtity where city = ?1", nativeQuery = true)
    public List<BaseCommuntity> findByCityCode(Integer cityCode);

    @Modifying
    @Query(value = "update BaseCommuntity b set b.code = ?1 where b.id = ?2")
    void modityCode(String code, Integer id);

    @Modifying
    @Query(value = "update BaseCommuntity b set b.flag = ?1 where b.id = ?2")
    void modityFlag(String flag, Integer id);


    BaseCommuntity findByCode(String code);


    @Query(value = "select * from base_communtity where city = ?1 and name like CONCAT('%',?2,'%')", nativeQuery = true)
    public Page<BaseCommuntity> findByCityCodeAndName(Integer cityCode, String name, Pageable pageable);

    @Query(value = "select * from base_communtity where city = ?1 and name like CONCAT('%',?2,'%')", nativeQuery = true)
    public List<BaseCommuntity> findByCityCodeAndName(Integer cityCode, String name);
}
