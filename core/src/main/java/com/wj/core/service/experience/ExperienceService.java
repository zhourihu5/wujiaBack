package com.wj.core.service.experience;

import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.entity.message.Message;
import com.wj.core.repository.activity.BlackListRepository;
import com.wj.core.repository.experience.ExperienceCodeRepository;
import com.wj.core.repository.experience.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

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

    public Page<Experience> findAllByStatus(String status, Pageable pageable) {
        Page<Experience> page = null;
        if (status != null) {
            page = experienceRepository.findAllByStatus(status, pageable);
        } else {
            page = experienceRepository.findAll(pageable);
        }
        return page;
    }

    @Transactional
    public void updateExperienceCode(ExperienceCode experienceCode) {
        experienceCode.setUpdateDate(new Date());
        experienceCodeRepository.updateExperienceCode(experienceCode.getUserId(), experienceCode.getUserName(), experienceCode.getUpdateDate(), experienceCode.getId());
    }

}
