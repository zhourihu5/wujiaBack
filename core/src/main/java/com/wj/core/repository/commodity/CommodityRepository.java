package com.wj.core.repository.commodity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.commodity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommodityRepository extends JpaRepository<Commodity, Integer>, JpaSpecificationExecutor<Activity> {

    @Query(value = "select * from ebiz_commodity where id = ?1", nativeQuery = true)
    Commodity findByCommodityId(Integer id);

    @Query("update Commodity c set c.status = ?1 where c.id = ?2")
    @Modifying
    void modityStatus(String status, Integer id);


    Commodity findByCode(String code);

    @Query(value = "update ebiz_commodity set sales_num = sales_num + ?1 where id = ?2", nativeQuery = true)
    @Modifying
    void moditySaleNum(Integer num, Integer id);

}
