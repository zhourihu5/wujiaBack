package com.wj.core.repository.commodity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.commodity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {

    @Query(value = "select * from ebiz_commodity where id = ?1", nativeQuery = true)
    public Commodity findByCommodityId(Integer id);
}
