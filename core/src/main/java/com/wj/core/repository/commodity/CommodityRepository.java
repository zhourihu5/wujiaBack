package com.wj.core.repository.commodity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.commodity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {
}
