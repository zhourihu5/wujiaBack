package com.wj.core.repository.op;

import com.wj.core.entity.op.OpFamilyService;
import com.wj.core.entity.op.OpService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FamilyServeRepository extends JpaRepository<OpFamilyService, Integer> {


    /**
     * 向家庭服务订阅表中添加数据
     * @param
     * @return Integer
     */
    @Modifying
    @Query(value = "insert into op_family_service(service_id, user_id, is_subscribe) values(?1,?2,?3)", nativeQuery = true)
    public Integer insertFamilyService(Integer serviceId, Integer userId, Integer isSubscribe);

    /**
     * 删除数据
     * @param serviceId
     * @param userId
     * @return void
     */
    @Modifying
    @Query(value = "delete from op_family_service where service_id = ?1 and user_id = ?2", nativeQuery = true)
    public void deleteFamilyService(Integer serviceId, Integer userId);


    /**
     * 根据服务ID查询订阅人数
     * @param serviceId
     * @return Integer
     */
    @Query(value = "select count(*) from op_family_service where service_id = ?1 and is_subscribe = 1", nativeQuery = true)
    public Integer findCountByServiceId(Integer serviceId);

    /**
     * 根据服务ID查询用户订阅列表
     * @param serviceId
     * @return List<OpFamilyService>
     */
    @Query(value = "select * from op_family_service where service_id = ?1  and is_subscribe = 1", nativeQuery = true)
    public List<OpFamilyService> findListByServiceId(Integer serviceId);
}
