package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.repository.base.BaseCommuntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseCommuntityService {
    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    /**
     * 根据id查询社区信息
     * @param id
     * @return BaseCommuntity
     */
    public BaseCommuntity findById(Integer id) {
        return baseCommuntityRepository.findByCommuntityId(id);
    }


}
