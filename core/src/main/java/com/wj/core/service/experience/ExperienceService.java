package com.wj.core.service.experience;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.entity.message.Message;
import com.wj.core.repository.activity.BlackListRepository;
import com.wj.core.repository.experience.ExperienceCodeRepository;
import com.wj.core.repository.experience.ExperienceRepository;
import com.wj.core.util.time.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceCodeRepository experienceCodeRepository;

    @Transactional
    public void saveExperience(Experience experience) {
        experience.setCreateDate(new Date());
        experience.setUpdateDate(new Date());
        Experience newEexperience = experienceRepository.save(experience);
        // 未完待续
        ExperienceCode experienceCode = new ExperienceCode();
        experienceCodeRepository.save(experienceCode);
    }

    public Page<Experience> findAll(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String name) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(startDate)) {
                try {
                    predicates.add(criteriaBuilder.equal(root.get("startDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, startDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(endDate)) {
                try {
                    predicates.add(criteriaBuilder.equal(root.get("endDate"), DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, endDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(status)) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (StringUtils.isNotBlank(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Experience> page = experienceRepository.findAll(specification, pageable);
        return page;
    }

    @Transactional
    public void updateExperienceCode(ExperienceCode experienceCode) {
        experienceCode.setUpdateDate(new Date());
        experienceCodeRepository.updateExperienceCode(experienceCode.getUserId(), experienceCode.getUserName(), experienceCode.getUpdateDate(), experienceCode.getId());
    }

}
