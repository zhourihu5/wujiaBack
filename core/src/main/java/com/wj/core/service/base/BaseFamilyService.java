package com.wj.core.service.base;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.repository.base.BaseFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BaseFamilyService {

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    /**
     * 保存家庭信息
     *
     * @param family
     * @return void
     */
    @Transactional
    public void saveFamily(BaseFamily family) {
        baseFamilyRepository.save(family);
    }

    public BaseFamily findByFamilyId(Integer fid) {
        return baseFamilyRepository.findByFamilyId(fid);
    }


}
