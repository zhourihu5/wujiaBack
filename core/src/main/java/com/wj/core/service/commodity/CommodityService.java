package com.wj.core.service.commodity;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.entity.commodity.Lables;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.commodity.LablesRepository;
import com.wj.core.util.time.ClockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private LablesRepository lablesRepository;

    public Commodity findById(Integer commodityId) {
        return commodityRepository.findByCommodityId(commodityId);
    }


    public void saveLables(Lables lables) {
        if (lables.getId() == null) {
            lables.setCreateDate(ClockUtil.currentDate());
        }
        lablesRepository.save(lables);
    }

    public Page<Lables> getLables(Integer pageSize, Integer pageNo) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "id");
        return lablesRepository.findAll(page);
    }

    public void removeLables(Integer id) {
        lablesRepository.deleteById(id);
    }

    public void saveCommodity(Commodity commodity) {

    }


}
