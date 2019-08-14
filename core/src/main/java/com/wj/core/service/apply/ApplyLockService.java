package com.wj.core.service.apply;

import com.google.common.collect.Lists;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.repository.apply.ApplyUnlockRepository;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.QstBindingUserService;
import com.wj.core.util.time.ClockUtil;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ApplyLockService {

    @Autowired
    private ApplyUnlockRepository applyUnlockRepository;
    @Autowired
    private UserFamilyRepository userFamilyRepository;
    @Autowired
    private QstBindingUserService qstBindingUserService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private BaseFamilyRepository baseFamilyRepository;
    @Autowired
    private BaseUnitRepository baseUnitRepository;

    @Transactional
    public void saveApplyLock(ApplyLock applyLock) {
        applyLock.setStatus("0");
        applyLock.setCreateDate(new Date());
        applyUnlockRepository.save(applyLock);
    }

    public Page<ApplyLock> getList(Integer pageNum, Integer pageSize, Date startDate, Date endDate, String status, String userName) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (startDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("createDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("createDate"), endDate));
            }
            if (StringUtils.isNotBlank(userName)) {
                predicates.add(criteriaBuilder.equal(root.get("applyName"), userName));
            }
            if (StringUtils.isNotBlank(status)) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "createDate");
        return applyUnlockRepository.findAll(specification, page);
    }

    @Transactional
    public void modityStatus(String status, String remark, Integer id) {
        if (status.equals("1")) {
            ApplyLock applyLock = applyUnlockRepository.findByApplyId(id);
            SysUserFamily sysUserFamily = new SysUserFamily();
            UserFamily userFamily = new UserFamily();
            userFamily.setFamilyId(applyLock.getFamilyId());
            userFamily.setUserId(applyLock.getUserId());
            sysUserFamily.setUserFamily(userFamily);
            sysUserFamily.setIdentity(0);
            userFamilyRepository.save(sysUserFamily);
            //全视通
            SysUserInfo userInfo = userInfoRepository.findByUserId(applyLock.getUserId());
            BaseFamily baseFamily = baseFamilyRepository.findByFamilyId(applyLock.getFamilyId());
            String unitCode = baseFamily.getCode().substring(0, 16);
            BaseUnit baseUnit = baseUnitRepository.findByUnitCode(unitCode);
            Map<String, Object> result = qstBindingUserService.userRooms(userInfo.getUserName(), baseUnit.getDirectory());
            if (Integer.valueOf(result.get("Code").toString()) != 200) {
                throw new ServiceException("同步全视通数据错误", ErrorCode.QST_ERROR);
            }
            applyUnlockRepository.updateStatus(status, id);
        } else {
            applyUnlockRepository.updateStatusAndRemark(status, remark, id);
        }

    }


    public List<ApplyLock> findByUserId(Integer userId) {
        return applyUnlockRepository.findByUserId(userId);
    }

    public Integer findByUserIdAndFamilyId(Integer userId, Integer familyId, String status) {
        return applyUnlockRepository.findByUserIdAndFamilyId(userId, familyId, status);
    }
}
