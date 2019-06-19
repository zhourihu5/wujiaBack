package com.wj.core.service.op;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.enums.*;
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
     * 根据家庭id查询我的服务列表
     *
     * @param fid
     * @return OpService
     */
    public List<OpService> findByFamilyId(Integer fid) {
        BaseFamily baseFamily = baseFamilyRepository.getOne(fid);
        List<OpService> serviceList = baseFamily.getServiceId();
        return serviceList;
    }

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
//        List<ServiceDTO> list = new ArrayList<>();
//        List<OpService> serviceList = new ArrayList<>();
        if (type == ServiceType.ONE.toInt()) {
            page = serviceRepository.findByUserId(userId, pageable);
//            SysUserInfo userInfo = userInfoRepository.getOne(uid);
//            serviceList = userInfo.getServiceId();
//            ServiceDTO serviceDTO = new ServiceDTO();
//            serviceDTO.setTypeId(1);
//            serviceDTO.setTypeName(ServiceType.getName(type));
//            serviceDTO.setList(serviceList);
//            list.add(serviceDTO);
        } else if (type == ServiceType.TWO.toInt()) {
            List<OpBanner> bannerList = bannerRepository.findByModuleIdList(1);
            serviceAllDTO.setBannerList(bannerList);
//            for (int i = 1; i <= FindType.values().length; i++) {
            page = serviceRepository.findByType(1, pageable);
//                ServiceDTO serviceDTO = new ServiceDTO();
//                serviceDTO.setTypeId(i);
//                serviceDTO.setTypeName(FindType.getName(i));
//                serviceDTO.setList(serviceList);
//                list.add(serviceDTO);
//            }
        } else if (type == ServiceType.THREE.toInt()) {
//            for (int i = 1; i <= GovernmentType.values().length; i++) {
            page = serviceRepository.findByType(2, pageable);
//                ServiceDTO serviceDTO = new ServiceDTO();
//                serviceDTO.setTypeId(i);
//                serviceDTO.setTypeName(GovernmentType.getName(i));
//                serviceDTO.setList(serviceList);
//                list.add(serviceDTO);
//            }
        } else {
            page = serviceRepository.findAll(pageable);
//            for (int i = 1; i <= AllServiceType.values().length; i++) {
//                serviceList = serviceRepository.findByTypeAndCategory(type, i);
//                ServiceDTO serviceDTO = new ServiceDTO();
//                serviceDTO.setTypeId(i);
//                serviceDTO.setTypeName(AllServiceType.getName(i));
//                serviceDTO.setList(serviceList);
//                list.add(serviceDTO);
//            }
        }
        for (OpService service: page) {
            OpFamilyService familyService = familyServeRepository.findByServiceIdAndUserId(service.getId(), userId);
            if (familyService == null) {
                service.setIsSubscribe(0);
            } else {
                service.setIsSubscribe(familyService.getIsSubscribe());
            }
        }
        serviceAllDTO.setPage(page);
//        serviceAllDTO.setServiceList(list);
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

}
