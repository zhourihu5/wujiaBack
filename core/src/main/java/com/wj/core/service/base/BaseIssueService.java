package com.wj.core.service.base;

import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseIssue;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseDistrictRepository;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.repository.base.BaseIssueRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BaseIssueService {

    @Autowired
    private BaseIssueRepository baseIssueRepository;

    @Autowired
    private BaseDistrictRepository baseDistrictRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    @Autowired
    private BaseFloorRepository baseFloorRepository;

    @Transactional
    public void saveIssue(BaseIssue issue) {
        if (issue.getId() == null) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(issue.getCode().substring(0, 6));
            System.out.println("---------" + issue.getCode().substring(0, 6));
            Integer count = baseIssueRepository.findCountByCommuntityId(issue.getCommuntityId());
            String number = "";
            if (count == null || count == 0) {
                number = "01";
            } else if (count > 0 && count < 10) {
                number = "0" + (count + 1);
            } else if (count > 10) {
                number = "" + (count + 1);
            }
            sBuffer.append(number);
            sBuffer.append("000000000000");
            System.out.println("sBuffer++++++++++++++" + sBuffer);
            issue.setCode(sBuffer.toString());
        }
        issue.setCreateDate(new Date());
        baseIssueRepository.save(issue);
    }

    public Page<BaseIssue> findAll(Integer communtityId, Pageable pageable) {
        Page<BaseIssue> page = null;
        if (communtityId != null) {
            page = baseIssueRepository.findByCommuntityId(communtityId, pageable);
        } else {
            page = baseIssueRepository.findAll(pageable);
        }
        for (BaseIssue baseIssue : page) {
            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseIssue.getCommuntityId());
            if (communtity == null)
                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            baseIssue.setCommuntityName(communtity.getName());
        }
        return page;
    }

    public List<BaseIssue> findByCommuntityId(Integer communtityId) {
        List<BaseIssue> issueList = baseIssueRepository.findByCommuntityId(communtityId);
        for (BaseIssue baseIssue : issueList) {
            Integer districtCount = baseDistrictRepository.findCountByIssueId(baseIssue.getId());
            if (districtCount != null && districtCount > 0) {
                //有区
                baseIssue.setNodeDisplay("区");
                continue;
            }
            Integer floorCount = baseFloorRepository.findCountByIssueId(baseIssue.getId());
            if (floorCount != null && floorCount > 0) {
                //有楼
                baseIssue.setNodeDisplay("楼");
                continue;
            }
            baseIssue.setNodeDisplay("无");
        }
        return issueList;
    }

    public BaseIssue findById(Integer id) {
        BaseIssue baseIssue = baseIssueRepository.findByIssueId(id);
        Integer districtCount = baseDistrictRepository.findCountByIssueId(baseIssue.getId());
        if (districtCount != null && districtCount > 0) {
            //有区
            baseIssue.setNodeDisplay("区");
            return baseIssue;
        }
        Integer floorCount = baseFloorRepository.findCountByIssueId(baseIssue.getId());
        if (floorCount != null && floorCount > 0) {
            //有楼
            baseIssue.setNodeDisplay("楼");
            return baseIssue;
        }
        baseIssue.setNodeDisplay("无");
        return baseIssue;
    }
}
