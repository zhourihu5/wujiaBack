package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseDistrictRepository;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BaseDistrictService {

    @Autowired
    private BaseDistrictRepository baseDistrictRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    @Transactional
    public void saveDistrict(BaseDistrict district) {
        district.setCreateDate(new Date());
        baseDistrictRepository.save(district);
    }

    public Page<BaseDistrict> findAll(Integer issueId, Pageable pageable) {
        Page<BaseDistrict> page = null;
        if (issueId != null) {
            page = baseDistrictRepository.findByIssueId(issueId, pageable);
        } else {
            page = baseDistrictRepository.findAll(pageable);
        }
//        for (BaseDistrict baseDistrict : page) {
//            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
//            if (communtity == null)
//                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
//            baseFloor.setCommuntityName(communtity.getName());
//        }
        return page;
    }

    public List<BaseDistrict> findByIssueId(Integer issueId) {
        return baseDistrictRepository.findByIssueId(issueId);
    }
}
