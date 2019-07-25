package com.wj.core.service.commodity;

import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

}
