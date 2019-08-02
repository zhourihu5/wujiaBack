package com.wj.core.service.base;

import com.google.common.collect.Lists;
import com.wj.core.entity.base.BaseCommuntityInfo;
import com.wj.core.repository.base.BaseCommuntityInfoRepository;
import com.wj.core.util.time.ClockUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class BaseCommuntityInfoService {

    @Autowired
    private BaseCommuntityInfoRepository baseCommuntityInfoRepository;


    public Page<BaseCommuntityInfo> findAll(Integer communtityId, Pageable pageable) {
        return baseCommuntityInfoRepository.findAll(communtityId, pageable);
    }

    public List<BaseCommuntityInfo> findList(Integer communtityId) {
        return baseCommuntityInfoRepository.findList(communtityId);
    }


    public void save(BaseCommuntityInfo baseCommuntityInfo) {
        if (baseCommuntityInfo.getId() == null) {
            baseCommuntityInfo.setCreateDate(ClockUtil.currentDate());
        }
        baseCommuntityInfoRepository.save(baseCommuntityInfo);
    }


    public Page<BaseCommuntityInfo> getList(Integer pageNum, Integer pageSize, String code) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(code)) {
                predicates.add(criteriaBuilder.equal(root.get("communityCode"), code));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        return baseCommuntityInfoRepository.findAll(specification, page);
    }

    public void remove(Integer id) {
        baseCommuntityInfoRepository.deleteById(id);
    }


}
