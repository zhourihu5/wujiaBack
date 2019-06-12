package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.repository.base.BaseAreaRepository;
import com.wj.core.repository.base.FamilyCommuntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyCommuntityService {
    @Autowired
    private FamilyCommuntityRepository familyCommuntityRepository;


    /**
     * 根据家庭id查询指定社区
     * @param fid
     * @return Integer
     */
    public Integer findByFamilyId(Integer fid) {
        return familyCommuntityRepository.findByFamilyId(fid);
    }

}
