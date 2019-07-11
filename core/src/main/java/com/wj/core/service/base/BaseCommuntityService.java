package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.SysFamilyCommuntity;
import com.wj.core.entity.base.embeddable.FamilyCommuntity;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.SysUserInfoDTO;
import com.wj.core.repository.base.*;
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

    @Autowired
    private BaseIssueRepository baseIssueRepository;

    @Autowired
    private BaseDistrictRepository baseDistrictRepository;


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
     * @return List<Map<String, BaseCommuntity>>
     */
    public List<Map<String, BaseCommuntity>> findByAreaCodeV2(Integer areaCode) {
        List<BaseCommuntity> communtityList = baseCommuntityRepository.findByAreaCode(areaCode);
        List<Map<String, BaseCommuntity>> list = new ArrayList<Map<String, BaseCommuntity>>();
        for (BaseCommuntity baseCommuntity: communtityList) {
            Map<String, BaseCommuntity> map = new HashMap<>();
            Integer issueCount = baseIssueRepository.findCountByCommuntityId(baseCommuntity.getId());
            if (issueCount > 0) {
                map.put("期", baseCommuntity);
                //有期
                break;
            }
            // 没有期
            Integer districtCount = baseDistrictRepository.findCountByCommuntityId(baseCommuntity.getId());
            if (districtCount > 0) {
                map.put("区", baseCommuntity);
                //有区
                break;
            }
            // 没有期和区就是楼了
            map.put("楼", baseCommuntity);
        }
        return list;
    }
    public List<BaseCommuntity> findByAreaCode(Integer areaCode) {
        List<BaseCommuntity> communtityList = baseCommuntityRepository.findByAreaCode(areaCode);
        for (BaseCommuntity baseCommuntity: communtityList) {
            Integer issueCount = baseIssueRepository.findCountByCommuntityId(baseCommuntity.getId());
            if (issueCount != null && issueCount > 0) {
                //有期
                baseCommuntity.setNodeDisplay("期");
                break;
            }
            // 没有期
            Integer districtCount = baseDistrictRepository.findCountByCommuntityId(baseCommuntity.getId());
            if (districtCount != null && districtCount > 0) {
                //有区
                baseCommuntity.setNodeDisplay("区");
                break;
            }
            // 没有期和区就是楼了
            baseCommuntity.setNodeDisplay("楼");
        }
        return communtityList;
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
