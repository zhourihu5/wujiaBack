package com.wj.core.service.experience;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.Coupon;
import com.wj.core.entity.activity.dto.CouponMessageDTO;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.experience.ExperienceCode;
import com.wj.core.entity.experience.dto.ExperienceMessageDTO;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.helper.impl.RedisHelperImpl;
import com.wj.core.repository.experience.ExperienceCodeRepository;
import com.wj.core.repository.experience.ExperienceRepository;
import com.wj.core.service.activity.CouponTask;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.job.JobService;
import com.wj.core.util.CommonUtils;
import com.wj.core.util.time.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExperienceService {
    @Value("${wj.oss.access}")
    private String url;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceCodeRepository experienceCodeRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    private RedisHelperImpl redisHelper;

    @Transactional
    public void saveExperience(Experience experience) {
        if (experience.getId() != null && experience.getIsShow().equals("1")) {
            throw new ServiceException("上架状态不能编辑", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (experience.getStatus() == null) {
            experience.setStatus("0");
        }
        if (experience.getIsShow() == null) {
            experience.setIsShow("0");
        }
        if (experience.getDian() == null) {
            experience.setDian("0");
        }
        if (experience.getReceive() == null) {
            experience.setReceive("2");
        }
        if (StringUtils.isNotBlank(experience.getBanner()) && StringUtils.contains(experience.getBanner(), "https://")) {
            experience.setBanner(experience.getBanner());
        } else {
            experience.setBanner(url + experience.getBanner());
        }
        if (StringUtils.isNotBlank(experience.getCover()) && StringUtils.contains(experience.getCover(), "https://")) {
            experience.setCover(experience.getCover());
        } else {
            experience.setCover(url + experience.getCover());
        }
//        if (StringUtils.isNotBlank(experience.getImg1()) && StringUtils.contains(experience.getImg1(),"https://")) {
//            experience.setImg1(experience.getImg1());
//        } else {
//            experience.setImg1(url + experience.getImg1());
//        }
//        if (StringUtils.isNotBlank(experience.getImg2()) && StringUtils.contains(experience.getImg2(),"https://")) {
//            experience.setImg2(experience.getImg2());
//        } else {
//            experience.setImg2(url + experience.getImg2());
//        }
        experience.setCreateDate(new Date());
        experience.setUpdateDate(new Date());
        Experience newEexperience = experienceRepository.save(experience);
        if (experience.getId() != null) {
            experienceCodeRepository.deleteByExperienceId(experience.getId());
            redisHelper.remove("experience_" + experience.getId());
        }
        for (int i = 0; i < experience.getExperienceCodes().length; i++) {
            ExperienceCode experienceCode = new ExperienceCode();
            experienceCode.setExperienceId(newEexperience.getId());
            experienceCode.setExperienceCode(experience.getExperienceCodes()[i]);
            experienceCode.setCreateDate(new Date());
            experienceCode.setUpdateDate(new Date());
            experienceCode.setFinishDate(experience.getEndDate());
            ExperienceCode newExperienceCode = experienceCodeRepository.save(experienceCode);
            redisHelper.listPush("experience_" + experience.getId(), newExperienceCode.getExperienceCode().replaceAll("\"","&quot;"));
        }
        boolean ex = jobService.checkExists("experience_close_" + newEexperience.getId(), "experience");
        // 添加定时任务
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName("experience_close_" + newEexperience.getId());
        taskEntity.setJobGroup("experience");
        taskEntity.setJobClass(new ExperienceTask().getClass().getName());
        taskEntity.setObjectId(newEexperience.getId());
        taskEntity.setCronExpression(DateFormatUtil.formatDate(DateFormatUtil.CRON_DATE_FORMAT, newEexperience.getEndDate()));
        if (!ex) {
            jobService.addTask(taskEntity);
        } else {
            jobService.updateTask(taskEntity);
        }

        boolean ex1 = jobService.checkExists("experience_starting_" + newEexperience.getId(), "experience");
        // 添加定时任务
        TaskEntity taskEntity1 = new TaskEntity();
        taskEntity1.setJobName("experience_starting_" + newEexperience.getId());
        taskEntity1.setJobGroup("experience");
        taskEntity1.setJobClass(new ExperienceStartingTask().getClass().getName());
        taskEntity1.setObjectId(newEexperience.getId());
        taskEntity1.setCronExpression(DateFormatUtil.formatDate(DateFormatUtil.CRON_DATE_FORMAT, newEexperience.getStartDate()));
        if (!ex1) {
            jobService.addTask(taskEntity1);
        } else {
            jobService.updateTask(taskEntity1);
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


    public Page<Experience> getExperienceListByCommunity(Integer community, Integer pageNum, Integer pageSize) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(criteriaBuilder.equal(root.get("communitys"), community));
//            predicates.add(criteriaBuilder.equal(root.get("status"), 1));
            predicates.add(criteriaBuilder.equal(root.get("isShow"), 1));
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
        return page;
    }


//    // 领取后更新数据领取人是谁
//    @Transactional
//    public void updateExperienceCode(ExperienceCode experienceCode) {
//        experienceCodeRepository.updateExperienceCode(experienceCode.getUserId(), experienceCode.getUserName(), new Date(), experienceCode.getId());
//    }

    @Transactional
    public void updateExperienceIsShow(Experience experience) {
        experienceRepository.updateExperienceIsShow(experience.getIsShow(), new Date(), experience.getId());
    }

    @Transactional
    public void removeExperience(Integer id) {
        experienceRepository.deleteById(id);
        experienceCodeRepository.deleteByExperienceId(id);
    }


    public Experience getExperienceById(Integer userId, Integer id) {
        Experience experience = experienceRepository.getById(id);
        // 领取过的人数
        Integer allCount = experienceCodeRepository.findCountByExperienceIdAndUserId(id);
        experience.setSendNum(allCount);
        experience.setSurplusNum(experience.getCount() - allCount);
        experience.setUserExperienceCount(experienceCodeRepository.findCountByExperienceIdAndUserId(id, userId));
        return experience;
    }

    // 领取体验券
    @Transactional
    public ExperienceMessageDTO receiveExperience(Integer userId, String userName, Integer experienceId) {
        ExperienceMessageDTO experienceMessageDTO = new ExperienceMessageDTO();
        // 先验证每个人领取多少张的限制
        Experience experience = experienceRepository.getById(experienceId);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(date);
        String endDate = formatter.format(experience.getEndDate());
        boolean isafter = CommonUtils.isDateAfter(currentTime, endDate);
        if (isafter) {
            throw new ServiceException("活动优惠券已经结束，您不能领取!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Integer count = experienceCodeRepository.findCountByExperienceIdAndUserId(experienceId, userId);
        if (count >= experience.getLimitNum()) {
            throw new ServiceException("已领取", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Integer allCount = experienceCodeRepository.findCountByExperienceIdAndUserId(experienceId);
        if (allCount >= experience.getCount()) {
            experienceMessageDTO.setFlag(false);
        } else {
            String code = (String) redisHelper.listLPop("experience_" + experienceId);
            String newCode = code.replaceAll("\"","");
            ExperienceCode newExperienceCode = experienceCodeRepository.findExperienceByCode(newCode);
            experienceCodeRepository.updateExperienceCodeByCode(userId, userName, new Date(), newCode);
            experienceMessageDTO.setExperienceCode(newExperienceCode);
//            List<ExperienceCode> experienceCodes = experienceCodeRepository.findByUserIdNull(experienceId);
//            if (experienceCodes.size() > 0) {
//                experienceCodeRepository.updateExperienceCodeByCode(userId, userName, new Date(), code);
////                experienceCodeRepository.updateExperienceCodeByCode(userId, userName, new Date(), experienceCodes.get(0).getExperienceCode());
//            } else {
//                experienceMessageDTO.setFlag(false);
////            throw new ServiceException("很遗憾，已经被抢购一空", ErrorCode.INTERNAL_SERVER_ERROR);
//            }
//            experienceMessageDTO.setExperienceCode(experienceCodes.get(0));
        }
        return experienceMessageDTO;
    }

    // 领取体验券用户列表
    public Page<ExperienceCode> findByExperienceIdAndUserId(Integer experienceId, Pageable pageable) {
        Page<ExperienceCode> page = experienceCodeRepository.findByExperienceIdAndUserId(experienceId, pageable);
        return page;
    }

    @Transactional
    public void updateExperienceIsShowAndStatus(String isShow, String status, Date date, Integer id) {
        experienceRepository.updateExperienceIsShowAndStatus(isShow, status, date, id);
    }


}
