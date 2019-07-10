package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.SysFamilyCommuntity;
import com.wj.core.entity.base.embeddable.FamilyCommuntity;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.SysUserInfoDTO;
import com.wj.core.repository.base.BaseAreaRepository;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.FamilyCommuntityRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BaseCommuntityService {

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    @Autowired
    private BaseAreaRepository baseAreaRepository;

    @Autowired
    private FamilyCommuntityRepository familyCommuntityRepository;

    @Autowired
    private UserFamilyRepository userFamilyRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;


    /**
     * 根据id查询社区信息
     *
     * @param id
     * @return BaseCommuntity
     */
    public BaseCommuntity findById(Integer id) {
        return baseCommuntityRepository.findByCommuntityId(id);
    }

    /**
     * 保存社区信息
     *
     * @param communtity
     * @return void
     */
    @Transactional
    public void saveCommuntity(BaseCommuntity communtity) {
        communtity.setCreateDate(new Date());
        baseCommuntityRepository.save(communtity);
    }

    /**
     * 获取社区分页信息
     *
     * @param areaCode
     * @param name
     * @return Page<BaseCommuntity>
     */
    public Page<BaseCommuntity> findAll(Integer areaCode, String name, Pageable pageable) {
        Page<BaseCommuntity> page = null;
        if (areaCode != null && !CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByAreaCodeAndName(areaCode, name, pageable);
        } else if (areaCode != null && CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByAreaCode(areaCode, pageable);
        } else if (areaCode == null && !CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByName(name, pageable);
        } else {
            page = baseCommuntityRepository.findAll(pageable);
        }
        for (BaseCommuntity baseCommuntity : page) {
            BaseArea area = baseAreaRepository.findByCityId(baseCommuntity.getArea());
            BaseArea city = baseAreaRepository.findByCityId(baseCommuntity.getCity());
            BaseArea provice = baseAreaRepository.findByCityId(baseCommuntity.getProvince());
            if (area != null) baseCommuntity.setAreaName(area.getAreaName());
            if (city != null) baseCommuntity.setCityName(city.getAreaName());
            if (provice != null) baseCommuntity.setProvinceName(provice.getAreaName());

        }
        return page;
    }

    /**
     * 根据市code查询当前所有社区
     *
     * @param areaCode
     * @return List<BaseCommuntity>
     */
    public List<BaseCommuntity> findByAreaCode(Integer areaCode) {
        return baseCommuntityRepository.findByAreaCode(areaCode);
    }

    /**
     * 根据社区ID查询当前社区所有用户
     *
     * @param communtityId
     * @return List<SysUserInfo>
     */
//    public List<SysUserInfo> findUserListByCid(Integer communtityId) {
//        List<SysUserInfo> list = new ArrayList<SysUserInfo>();
//        List<SysFamilyCommuntity> familyCommuntityList = familyCommuntityRepository.findByCommuntityId(communtityId);
//        familyCommuntityList.forEach(SysFamilyCommuntity -> {
//            List<SysUserFamily> userFamilyList = userFamilyRepository.findByFamilyId(SysFamilyCommuntity.getFamilyCommuntity().getFamilyId());
//            userFamilyList.forEach(SysUserFamily -> {
//                SysUserInfo sysUserInfo = userInfoRepository.findByUserId(SysUserFamily.getUserFamily().getUserId());
//                list.add(sysUserInfo);
//            });
//        });
//        return list;
//    }
    public List<SysUserInfoDTO> findUserListByCid(Integer communtityId) {
        List<SysUserInfoDTO> list = new ArrayList<SysUserInfoDTO>();
        List<SysFamilyCommuntity> familyCommuntityList = familyCommuntityRepository.findByCommuntityId(communtityId);
        familyCommuntityList.forEach(SysFamilyCommuntity -> {
            List<SysUserFamily> userFamilyList = userFamilyRepository.findByFamilyId(SysFamilyCommuntity.getFamilyCommuntity().getFamilyId());
            userFamilyList.forEach(SysUserFamily -> {
                SysUserInfoDTO sysUserInfoDTO = new SysUserInfoDTO();
                SysUserInfo sysUserInfo = userInfoRepository.findByUserId(SysUserFamily.getUserFamily().getUserId());
                sysUserInfo.setFid(SysUserFamily.getUserFamily().getFamilyId());
                sysUserInfoDTO.setId(sysUserInfo.getId());
                sysUserInfoDTO.setFid(SysUserFamily.getUserFamily().getFamilyId());
                list.add(sysUserInfoDTO);
            });
        });
        return list;
    }

}
