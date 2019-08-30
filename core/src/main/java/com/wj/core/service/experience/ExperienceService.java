package com.wj.core.service.experience;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.repository.experience.ExperienceCodeRepository;
import com.wj.core.repository.experience.ExperienceRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
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
        if (experience.getId() != null && experience.getIsShow().equals("1")) {
            throw new ServiceException("上架状态不能编辑", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (experience.getStatus() == null) {
            experience.setStatus("1");
        }
        if (experience.getIsShow() == null) {
            experience.setIsShow("0");
        }
        if (experience.getReceive() == null) {
            experience.setReceive("2");
        }
        experience.setCreateDate(new Date());
        experience.setUpdateDate(new Date());
        Experience newEexperience = experienceRepository.save(experience);
        if (experience.getId() != null) {
            experienceCodeRepository.deleteByExperienceId(experience.getId());
        }
        for (int i = 0; i < experience.getExperienceCodes().length; i++) {
            ExperienceCode experienceCode = new ExperienceCode();
            experienceCode.setExperienceId(newEexperience.getId());
            experienceCode.setExperienceCode(experience.getExperienceCodes()[i]);
            experienceCode.setCreateDate(new Date());
            experienceCodeRepository.save(experienceCode);
        }
    }

    public Page<Experience> getExperienceList(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String name) {
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
            // 状态9是删除
            predicates.add(criteriaBuilder.notEqual(root.get("isShow"), 9));
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
        page.forEach(Experience -> {
            List<ExperienceCode> experienceCodes = experienceCodeRepository.findByExperienceIdAndUserId(Experience.getId());
            Experience.setExperienceCodeList(experienceCodes);
            List<ExperienceCode> experienceCodeList = experienceCodeRepository.findByExperienceId(Experience.getId());
            String arrStr[] = new String[experienceCodeList.size()];
            for (int i = 0; i < experienceCodeList.size(); i++) {
                arrStr[i] = experienceCodeList.get(i).getExperienceCode();
            }
            Experience.setExperienceCodes(arrStr);
        });
        return page;
    }

    // 领取后更新数据领取人是谁
    @Transactional
    public void updateExperienceCode(ExperienceCode experienceCode) {
        experienceCodeRepository.updateExperienceCode(experienceCode.getUserId(), experienceCode.getUserName(), new Date(), experienceCode.getId());
    }

    @Transactional
    public void updateExperienceIsShow(Experience experience) {
        experienceRepository.updateExperienceIsShow(experience.getIsShow(), new Date(), experience.getId());
    }

    @Transactional
    public void removeExperience(Integer id) {
        experienceRepository.deleteById(id);
    }


}
