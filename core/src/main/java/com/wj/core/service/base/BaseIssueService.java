package com.wj.core.service.base;

import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseIssue;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseDistrictRepository;
import com.wj.core.repository.base.BaseIssueRepository;
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
    private BaseCommuntityRepository baseCommuntityRepository;

    @Transactional
    public void saveIssue(BaseIssue issue) {
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
//        for (BaseDistrict baseDistrict : page) {
//            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
//            if (communtity == null)
//                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
//            baseFloor.setCommuntityName(communtity.getName());
//        }
        return page;
    }

    public List<BaseIssue> findByCommuntityId(Integer communtityId) {
        return baseIssueRepository.findByCommuntityId(communtityId);
    }
}
