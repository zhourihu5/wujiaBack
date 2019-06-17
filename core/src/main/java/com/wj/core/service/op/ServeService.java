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
     * @param id
     * @return OpService
     */
    public OpService findById(Integer id) {
        return serviceRepository.getOne(id);
    }

    /**
     * 全部服务
     * @param type
     * @param uid
     * @return ServiceAllDTO
     */
    public ServiceAllDTO allList(Integer type, Integer uid) {
        ServiceAllDTO serviceAllDTO = new ServiceAllDTO();
        List<ServiceDTO> list = new ArrayList<>();
        List<OpService> serviceList = new ArrayList<>();
        if (type == ServiceType.ONE.toInt()) {
            SysUserInfo userInfo = userInfoRepository.getOne(uid);
            serviceList = userInfo.getServiceId();
            ServiceDTO serviceDTO = new ServiceDTO();
            serviceDTO.setTypeId(1);
            serviceDTO.setTypeName(ServiceType.getName(type));
            serviceDTO.setList(serviceList);
            list.add(serviceDTO);
        } else if (type == ServiceType.TWO.toInt()) {
            List<OpBanner> bannerList = bannerRepository.findByModuleIdList(1);
            for (int i = 1; i <= FindType.values().length; i++) {
                serviceList = serviceRepository.findByTypeAndCategory(type, i);
                ServiceDTO serviceDTO = new ServiceDTO();
                serviceDTO.setTypeId(i);
                serviceDTO.setTypeName(FindType.getName(i));
                serviceDTO.setList(serviceList);
                list.add(serviceDTO);
            }
            serviceAllDTO.setBannerList(bannerList);
        } else if (type == ServiceType.THREE.toInt()) {
            for (int i = 1; i <= GovernmentType.values().length; i++) {
                serviceList = serviceRepository.findByTypeAndCategory(type, i);
                ServiceDTO serviceDTO = new ServiceDTO();
                serviceDTO.setTypeId(i);
                serviceDTO.setTypeName(GovernmentType.getName(i));
                serviceDTO.setList(serviceList);
                list.add(serviceDTO);
            }
        } else {
            for (int i = 1; i <= AllServiceType.values().length; i++) {
                serviceList = serviceRepository.findByTypeAndCategory(type, i);
                ServiceDTO serviceDTO = new ServiceDTO();
                serviceDTO.setTypeId(i);
                serviceDTO.setTypeName(AllServiceType.getName(i));
                serviceDTO.setList(serviceList);
                list.add(serviceDTO);
            }
        }
        serviceAllDTO.setServiceList(list);
        return serviceAllDTO;
    }

    /**
     * 订阅/取消订阅服务
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
        familyService.setId(fs);
        return familyServeRepository.saveAndFlush(familyService);
    }

}
