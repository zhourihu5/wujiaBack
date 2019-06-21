package com.wj.core.service.op;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.enums.*;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.embeddable.FamilyService;
import com.wj.core.entity.op.OpFamilyService;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.ServiceAllDTO;
import com.wj.core.entity.user.dto.ServiceDTO;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.op.BannerRepository;
import com.wj.core.repository.op.FamilyServeRepository;
import com.wj.core.repository.op.ServeRepository;
import com.wj.core.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServeService {

    @Autowired
    private ServeRepository serviceRepository;

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private FamilyServeRepository familyServeRepository;

    @Autowired
    private BannerRepository bannerRepository;


    /**
     * 根据id查询服务详情
     *
     * @param id
     * @return OpService
     */
    public OpService findById(Integer id) {
        return serviceRepository.getOne(id);
    }

    /**
     * 根据类型查询服务
     *
     * @param type
     * @param userId
     * @return ServiceAllDTO
     */
    public ServiceAllDTO findListByType(Integer type, Integer userId, Pageable pageable) {
        ServiceAllDTO serviceAllDTO = new ServiceAllDTO();
        Page<OpService> page = null;
        if (type == ServiceType.ONE.toInt()) {
            page = serviceRepository.findByUserIdAAndIsSubscribe(userId, pageable);
        } else if (type == ServiceType.TWO.toInt()) {
            List<OpBanner> bannerList = bannerRepository.findByModuleIdList(1);
            serviceAllDTO.setBannerList(bannerList);
            page = serviceRepository.findByType(1, pageable);
        } else if (type == ServiceType.THREE.toInt()) {
            page = serviceRepository.findByType(2, pageable);
        } else {
            page = serviceRepository.findAll(pageable);
        }
        for (OpService service : page) {
            OpFamilyService familyService = familyServeRepository.findByServiceIdAndUserId(service.getId(), userId);
            if (familyService == null) {
                service.setIsSubscribe(0);
            } else {
                service.setIsSubscribe(familyService.getIsSubscribe());
            }
        }
        serviceAllDTO.setPage(page);
        return serviceAllDTO;
    }

    /**
     * 订阅/取消订阅服务
     *
     * @param serviceId, userId, isSubscribe
     * @return Object
     */
    @Transactional
    public Object saveFamilyService(Integer serviceId, Integer userId, Integer isSubscribe) {
        OpFamilyService familyService = new OpFamilyService();
        familyService.setIsSubscribe(isSubscribe);
        FamilyService fs = new FamilyService();
        fs.setServiceId(serviceId);
        fs.setUserId(userId);
        familyService.setFamilyService(fs);
        return familyServeRepository.saveAndFlush(familyService);
    }

    /**
     * 添加/更新服务
     *
     * @param service
     * @return void
     */
    @Transactional
    public void saveService(OpService service) {
        serviceRepository.save(service);
    }

    /**
     * 全部服务
     *
     * @return List<OpService>
     */
    public List<OpService> findAll() {
        return serviceRepository.findAll();
    }

    /**
     * 根据服务ID查询订阅人数
     *
     * @param serviceId
     * @return Integer
     */
    public Integer findCountByServiceId(Integer serviceId) {
        return familyServeRepository.findCountByServiceId(serviceId);
    }

    /**
     * 根据服务ID查询用户订阅列表
     *
     * @param serviceId
     * @return Integer
     */
    public List<OpFamilyService> findListByServiceId(Integer serviceId) {
        return familyServeRepository.findListByServiceId(serviceId);
    }

    /**
     * 服务分页列表
     *
     * @param pageable
     * @return Page<OpService>
     */
    public Page<OpService> findAllPage(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }

}
