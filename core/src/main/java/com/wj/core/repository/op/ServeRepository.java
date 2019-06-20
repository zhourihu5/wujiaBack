package com.wj.core.repository.op;

import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ServeRepository extends JpaRepository<OpService, Integer> {


    /**
     * 发现/政务列表
     * @param type
     * @return Page<OpService>
     */
    @Query(value = "select * from op_service where type = ?1", nativeQuery = true)
    public Page<OpService> findByType(Integer type, Pageable pageable);

    /**
     *
     * @param type
     * @param category
     * @return List<OpService>
     */
    @Query(value = "select * from op_service where type = ?1 and category = ?2", nativeQuery = true)
    public List<OpService> findByTypeAndCategory(Integer type, Integer category);


    /**
     * 根据服务id查询服务分页列表
     * @param userId
     * @return Page<OpService>
     */
    @Query(value = "select * from op_service a,op_family_service b where a.id = b.service_id and b.user_id = ?1", nativeQuery = true)
    public Page<OpService> findByUserId(Integer userId, Pageable pageable);

    /**
     * 我的服务
     * @param userId
     * @return Page<OpService>
     */
    @Query(value = "select * from op_service a,op_family_service b where a.id = b.service_id and b.user_id = ?1 and b.is_subscribe = 1", nativeQuery = true)
    public Page<OpService> findByUserIdAAndIsSubscribe(Integer userId, Pageable pageable);

}
