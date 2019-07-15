package com.wj.core.service.base;

import com.wj.core.entity.base.*;
import com.wj.core.entity.base.dto.BaseFamilyDTO;
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

    @Autowired
    private BaseFloorRepository baseFloorRepository;

    @Autowired
    private BaseUnitRepository baseUnitRepository;



    /**
     * 根据id查询社区信息
     *
     * @param id
     * @return BaseCommuntity
     */
    public BaseCommuntity findById(Integer id) {
        BaseCommuntity baseCommuntity = baseCommuntityRepository.findByCommuntityId(id);
        Integer issueCount = baseIssueRepository.findCountByCommuntityId(baseCommuntity.getId());
        if (issueCount != null && issueCount > 0) {
            //有期
            baseCommuntity.setNodeDisplay("期");
            return baseCommuntity;
        }
        Integer districtCount = baseDistrictRepository.findCountByCommuntityId(baseCommuntity.getId());
        if (districtCount != null && districtCount > 0) {
            //有区
            baseCommuntity.setNodeDisplay("区");
            return baseCommuntity;
        }
        Integer floorCount = baseFloorRepository.findCountByCommuntityId(baseCommuntity.getId());
        if (floorCount != null && floorCount > 0) {
            //有楼
            baseCommuntity.setNodeDisplay("楼");
            return baseCommuntity;
        }
        baseCommuntity.setNodeDisplay("无");
        return baseCommuntity;
    }

    /**
     * 保存社区信息
     *
     * @param communtity
     * @return void
     */
    @Transactional
    public BaseCommuntity saveCommuntity(BaseCommuntity communtity) {
        if (communtity.getId() == null) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(CommonUtils.randomCode());
            sBuffer.append("00000000000000");
            System.out.println("sBuffer++++++++++++++" + sBuffer);
            communtity.setCode(sBuffer.toString());
        }
        communtity.setCreateDate(new Date());
        return baseCommuntityRepository.save(communtity);
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
     * @return List<Map < String, BaseCommuntity>>
     */
    public List<BaseCommuntity> findByAreaCode(Integer areaCode) {
        List<BaseCommuntity> communtityList = baseCommuntityRepository.findByAreaCode(areaCode);
        for (BaseCommuntity baseCommuntity : communtityList) {
            Integer issueCount = baseIssueRepository.findCountByCommuntityId(baseCommuntity.getId());
            if (issueCount != null && issueCount > 0) {
                //有期
                baseCommuntity.setNodeDisplay("期");
                continue;
            }
            Integer districtCount = baseDistrictRepository.findCountByCommuntityId(baseCommuntity.getId());
            if (districtCount != null && districtCount > 0) {
                //有区
                baseCommuntity.setNodeDisplay("区");
                continue;
            }
            Integer floorCount = baseFloorRepository.findCountByCommuntityId(baseCommuntity.getId());
            if (floorCount != null && floorCount > 0) {
                //有楼
                baseCommuntity.setNodeDisplay("楼");
                continue;
            }
            baseCommuntity.setNodeDisplay("无");
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

    /**
     * 根据社区查询当前社区所有家庭
     *
     * @param communtityCode
     * @return List
     */
    public List<BaseFamilyDTO> findFamilyListByCode(String communtityCode) {
        // 根据社区查询所有期
        List<BaseFamilyDTO> list = new ArrayList<>();
        List<BaseIssue> issueList = baseIssueRepository.findByCode(communtityCode.substring(0, 6));
        if (issueList.size() > 0) {
            for (BaseIssue baseIssue: issueList) {
                BaseFamilyDTO baseFamilyDTO = new BaseFamilyDTO();
                String name = baseIssue.getName();
                List<BaseDistrict> districtList = baseDistrictRepository.findByCode(baseIssue.getCode().substring(0, 8));
                if (districtList.size() > 0) {
                    for (BaseDistrict baseDistrict : districtList) {
                        name += baseDistrict.getName();
                        List<BaseFloor> floorList = baseFloorRepository.findByCode(baseDistrict.getCode().substring(0, 10));
                        for (BaseFloor baseFloor : floorList) {
                            name += baseFloor.getName();
                            List<BaseUnit> unitList = baseUnitRepository.findByCode(baseFloor.getCode().substring(0, 12));
                            for (BaseUnit baseUnit : unitList) {
                                name += baseUnit.getNum();
                                baseFamilyDTO.setName(name);
                            }
                        }
                    }
                } else {
                    List<BaseFloor> floorList = baseFloorRepository.findByCode(baseIssue.getCode().substring(0, 10));
                    for (BaseFloor baseFloor : floorList) {
                        name += baseFloor.getName();
                        List<BaseUnit> unitList = baseUnitRepository.findByCode(baseFloor.getCode().substring(0, 12));
                        for (BaseUnit baseUnit : unitList) {
                            name += baseUnit.getNum();
                            baseFamilyDTO.setName(name);
                        }
                    }
                }
                list.add(baseFamilyDTO);
            }
        } else {
            List<BaseDistrict> districtList = baseDistrictRepository.findByCode(communtityCode.substring(0, 8));
            if (districtList.size() > 0) {
                for (BaseDistrict baseDistrict : districtList) {
                    BaseFamilyDTO baseFamilyDTO = new BaseFamilyDTO();
                    String name = baseDistrict.getName();
                    List<BaseFloor> floorList = baseFloorRepository.findByCode(baseDistrict.getCode().substring(0, 10));
                    for (BaseFloor baseFloor : floorList) {
                        name += baseFloor.getName();
                        List<BaseUnit> unitList = baseUnitRepository.findByCode(baseFloor.getCode().substring(0, 12));
                        for (BaseUnit baseUnit : unitList) {
                            name += baseUnit.getNum();
                            baseFamilyDTO.setName(name);
                        }
                    }
                    list.add(baseFamilyDTO);
                }
            } else {
                List<BaseFloor> floorList = baseFloorRepository.findByCode(communtityCode.substring(0, 10));
                for (BaseFloor baseFloor : floorList) {
                    BaseFamilyDTO baseFamilyDTO = new BaseFamilyDTO();
                    String name = baseFloor.getName();
                    List<BaseUnit> unitList = baseUnitRepository.findByCode(baseFloor.getCode().substring(0, 12));
                    for (BaseUnit baseUnit : unitList) {
                        name += baseUnit.getNum();
                        baseFamilyDTO.setName(name);
                    }
                }
            }

        }
        return list;
    }

}
