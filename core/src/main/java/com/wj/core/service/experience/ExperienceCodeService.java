package com.wj.core.service.experience;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.CouponCode;
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
public class ExperienceCodeService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceCodeRepository experienceCodeRepository;

    public Page<ExperienceCode> findByUserIdStart(Integer userId, Pageable pageable) {
        Page<ExperienceCode> page =  experienceCodeRepository.findByUserIdStart(userId, new Date(), pageable);
        page.forEach(ExperienceCode -> {
            ExperienceCode.setExperience(experienceRepository.getById(ExperienceCode.getExperienceId()));
        });
        return page;
    }

    public Page<ExperienceCode> findByUserIdEnd(Integer userId, Pageable pageable) {
        Page<ExperienceCode> page =  experienceCodeRepository.findByUserIdEnd(userId, new Date(), pageable);
        page.forEach(ExperienceCode -> {
            ExperienceCode.setExperience(experienceRepository.getById(ExperienceCode.getExperienceId()));
        });
        return page;
    }

    public ExperienceCode findById(Integer id) {
        return experienceCodeRepository.getOne(id);
    }
}
