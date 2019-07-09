package com.wj.core.repository.op;

import com.wj.core.entity.op.OpService;
import com.wj.core.entity.op.OpServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ServiceCategoryRepository extends JpaRepository<OpServiceCategory, Integer> {


    /**
     * 根据ID查询服务类别
     * @param
     * @return int
     */
    @Query(value = "select * from op_service_category where id = ?1", nativeQuery = true)
    public OpServiceCategory findByCategory(Integer id);

    /**
     * 全部列表
     * @param
     * @return int
     */
    @Query(value = "select * from op_service_category", nativeQuery = true)
    public List<OpServiceCategory> findAllList();

    /**
     * 根据父ID查询服务
     * @param
     * @return int
     */
    @Query(value = "select * from op_service_category where pid = ?1", nativeQuery = true)
    public List<OpServiceCategory> findByPid(Integer pid);

}
