package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.entity.base.BaseIssue;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseDistrictRepository;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.repository.base.BaseIssueRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.base.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private BaseIssueRepository baseIssueRepository;


    // 保存区
    @Transactional
    public BaseDistrict saveDistrict(BaseDistrict district) {
        if (district.getId() == null) {
            if (district.getIssueId() != null) {
                Integer count = baseDistrictRepository.findCountByCommuntityId(district.getCommuntityId());
                BaseIssue bi = baseIssueRepository.getOne(district.getIssueId());
                district.setCode(CommunityUtil.genCode(bi.getCode(), ++count));
                baseCommuntityRepository.modityFlag("期-区", district.getCommuntityId());
            } else {
                BaseCommuntity bc = baseCommuntityRepository.getOne(district.getCommuntityId());
                Integer count = baseDistrictRepository.findCountByCommuntityId(district.getCommuntityId());
                district.setCode(CommunityUtil.genCode(bc.getCode().concat("00"), ++ count));
                baseCommuntityRepository.modityFlag("区", district.getCommuntityId());
            }
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

    public List<BaseDistrict> findByCommuntityId(String commCode, String issuCode) {
        if (StringUtils.isNotBlank(issuCode)) {
            return baseDistrictRepository.findByCodeLike(issuCode + "%");
        }
        return baseDistrictRepository.findByCodeLike(commCode + "%");

    }


    public List<BaseDistrict> findByIssueId(Integer issueId) {
        return baseDistrictRepository.findByIssueId(issueId);
    }

}
