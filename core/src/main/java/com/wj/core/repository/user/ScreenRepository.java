package com.wj.core.repository.user;

import com.wj.core.entity.user.SysScreen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScreenRepository extends JpaRepository<SysScreen, Integer> {


    /**
     * 根据社区id查询屏保
     *
     * @param communtityId
     * @return SysScreen
     */
    @Query(value = "select * from sys_screen where communtity_id = ?1", nativeQuery = true)
    List<SysScreen> findByCommuntityId(int communtityId);

    /**
     * 根据id查询屏保
     *
     * @param id
     * @return SysScreen
     */
    @Query(value = "select * from sys_screen where id = ?1", nativeQuery = true)
    SysScreen findById(int id);


    SysScreen findFirstByOrderByIdDesc();

}
