package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseIssue;
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
    public BaseDistrict saveDistrict(BaseDistrict district) {
        if (district.getId() == null) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(district.getCode().substring(0, 8));
            System.out.println("---------" + district.getCode().substring(0, 8));
            Integer count = 0;
            if (district.getCommuntityId() != null) {
                count = baseDistrictRepository.findCountByCommuntityId(district.getCommuntityId());
            } else if (district.getIssueId() != null){
                count = baseDistrictRepository.findCountByIssueId(district.getIssueId());
            }
            String number = "";
            if (count == null || count == 0) {
                number = "01";
            } else if (count > 0 && count < 10) {
                number = "0" + (count + 1);
            } else if (count > 10) {
                number = "" + (count + 1);
            }
            sBuffer.append(number);
            sBuffer.append("0000000000");
            System.out.println("sBuffer++++++++++++++" + sBuffer);
            district.setCode(sBuffer.toString());
        }
        district.setCreateDate(new Date());
        return baseDistrictRepository.save(district);
    }

    public Page<BaseDistrict> findAll(Integer communtityId, Integer issueId, Pageable pageable) {
        Page<BaseDistrict> page = null;
        if (communtityId != null) {
            page = baseDistrictRepository.findByCommuntityId(communtityId, pageable);
        } else if (issueId != null) {
            page = baseDistrictRepository.findByIssueId(issueId, pageable);
        } else {
            page = baseDistrictRepository.findAll(pageable);
        }
        return page;
    }

    public List<BaseDistrict> findByCommuntityId(Integer communtityId) {
        return baseDistrictRepository.findByCommuntityId(communtityId);
    }

    public List<BaseDistrict> findByIssueId(Integer issueId) {
        return baseDistrictRepository.findByIssueId(issueId);
    }

}
