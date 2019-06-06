package com.wj.core.repository.op;

import com.wj.core.entity.op.OpFamilyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FamilyServeRepository extends JpaRepository<OpFamilyService, Integer> {


    /**
     * 向家庭服务订阅表中添加数据
     * @param
     * @return int
     */
    @Modifying
    @Query(value = "insert into op_family_service(service_id, user_id, is_subscribe) values(?1,?2,?3)", nativeQuery = true)
    public int insertFamilyService(Integer serviceId, Integer userId, Integer isSubscribe);

    /**
     * 删除数据
     * @param
     * @return int
     */
    @Modifying
    @Query(value = "delete from op_family_service where service_id = ?1 and user_id = ?2", nativeQuery = true)
    public void deleteFamilyService(Integer serviceId, Integer userId);


}
