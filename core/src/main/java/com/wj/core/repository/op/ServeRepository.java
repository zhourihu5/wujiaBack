package com.wj.core.repository.op;

import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ServeRepository extends JpaRepository<OpService, Integer> {


//    /**
//     * 根据家庭id查询服务列表
//     * @param name
//     * @return SysUserInfo
//     */
//    @Query(value = "select * from sys_user_info where user_name = ?1", nativeQuery = true)
//    public SysUserInfo findByName(String name);
//
    /**
     * 发现列表
     * @param
     * @return int
     */
    @Query(value = "select * from op_service where type = ?1 and category = ?2", nativeQuery = true)
    public List<OpService> findByTypeAndCategory(Integer type, Integer category);


}
