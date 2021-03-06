package com.wj.core.service.base;

import com.fasterxml.jackson.databind.JavaType;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseIssue;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseDistrictRepository;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.repository.base.BaseIssueRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.QstCommuntityService;
import com.wj.core.service.qst.dto.TenantstructuresIssuseDTO;
import com.wj.core.util.CommonUtils;
import com.wj.core.util.base.CommunityUtil;
import com.wj.core.util.mapper.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private QstCommuntityService qstCommuntityService;
    static JsonMapper mapper = JsonMapper.defaultMapper();

    @Transactional
    public BaseIssue saveIssue(BaseIssue issue) {
        if (issue.getId() == null) {
            Integer count = baseIssueRepository.findCountByCommuntityId(issue.getCommuntityId());
            BaseCommuntity cm = baseCommuntityRepository.findByCommuntityId(issue.getCommuntityId());
            String issueCode = CommunityUtil.genCode(cm.getCode(), ++ count);
            baseCommuntityRepository.modityFlag("???", cm.getId());
            issue.setCode(issueCode);
            String r = qstCommuntityService.tenantstructures(cm.getDirectory(), 1, "???", 1, 2);
            if (StringUtils.contains(r,"[")) {
                JavaType type = mapper.buildCollectionType(List.class, Map.class);
                List<Map<String, Object>> list = mapper.fromJson(r, type);
                if (list.size() > 0) {
                    Map<String, Object> dto = list.get(0);
                    issue.setDirectory(dto.get("Directory").toString());
                    issue.setParentDirectory(dto.get("ParentDirectory").toString());
                    issue.setStructureId(Integer.valueOf(dto.get("StructureID").toString()));
                    issue.setStructureName(dto.get("StructureName").toString());
                }
            }
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
                throw new ServiceException("??????????????????", ErrorCode.INTERNAL_SERVER_ERROR);
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
            //??????
            baseIssue.setNodeDisplay("???");
            return baseIssue;
        }
        Integer floorCount = baseFloorRepository.findCountByIssueId(baseIssue.getId());
        if (floorCount != null && floorCount > 0) {
            //??????
            baseIssue.setNodeDisplay("???");
            return baseIssue;
        }
        baseIssue.setNodeDisplay("???");
        return baseIssue;
    }
}
