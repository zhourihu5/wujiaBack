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
import com.wj.core.util.base.CommunityUtil;
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
    public BaseIssue saveIssue(BaseIssue issue) {
        if (issue.getId() == null) {
            Integer count = baseIssueRepository.findCountByCommuntityId(issue.getCommuntityId());
            BaseCommuntity cm = baseCommuntityRepository.getOne(issue.getCommuntityId());
            String issueCode = CommunityUtil.genCode(cm.getCode(), ++ count);
            baseCommuntityRepository.modityFlag("期", cm.getId());
            issue.setCode(issueCode);
        }
        issue.setCreateDate(new Date());
        return baseIssueRepository.save(issue);
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

    public List<BaseIssue> findByCommuntityId(String commCode) {
        List<BaseIssue> issueList = baseIssueRepository.findByCodeLike(commCode + "%");
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
