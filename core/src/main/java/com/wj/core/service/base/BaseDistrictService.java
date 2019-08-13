package com.wj.core.service.base;

import com.fasterxml.jackson.databind.JavaType;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseDistrict;
import com.wj.core.entity.base.BaseIssue;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseDistrictRepository;
import com.wj.core.repository.base.BaseIssueRepository;
import com.wj.core.service.qst.QstCommuntityService;
import com.wj.core.service.qst.dto.TenantstructuresIssuseDTO;
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
public class BaseDistrictService {

    @Autowired
    private BaseDistrictRepository baseDistrictRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;
    @Autowired
    private BaseIssueRepository baseIssueRepository;
    @Autowired
    private QstCommuntityService qstCommuntityService;
    static JsonMapper mapper = JsonMapper.defaultMapper();


    // 保存区
    @Transactional
    public BaseDistrict saveDistrict(BaseDistrict district) {
        if (district.getId() == null) {
            Integer count = baseDistrictRepository.findCountByCommuntityId(district.getCommuntityId());
            String directory;
            if (district.getIssueId() != null) {
                BaseIssue bi = baseIssueRepository.getOne(district.getIssueId());
                district.setCode(CommunityUtil.genCode(bi.getCode(), ++count));
                baseCommuntityRepository.modityFlag("期-区", district.getCommuntityId());
                directory = bi.getDirectory();
            } else {
                BaseCommuntity bc = baseCommuntityRepository.getOne(district.getCommuntityId());
                district.setCode(CommunityUtil.genCode(bc.getCode().concat("00"), ++ count));
                baseCommuntityRepository.modityFlag("区", district.getCommuntityId());
                directory = bc.getDirectory();
            }
            String r = qstCommuntityService.tenantstructures(directory, 1, "区", 1, 2);
            if (StringUtils.contains(r,"[")) {
                JavaType type = mapper.buildCollectionType(List.class, Map.class);
                List<Map<String, Object>> list = mapper.fromJson(r, type);
                if (list.size() > 0) {
                    Map<String, Object> dto = list.get(0);
                    district.setDirectory(dto.get("Directory").toString());
                    district.setParentDirectory(dto.get("ParentDirectory").toString());
                    district.setStructureId(Integer.valueOf(dto.get("StructureId").toString()));
                    district.setStructureName(dto.get("StructureName").toString());
                }
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
