package com.wj.core.service.base;

import com.google.common.collect.Lists;
import com.wj.core.entity.base.*;
import com.wj.core.entity.base.dto.BaseFamilyDTO;
import com.wj.core.entity.base.dto.QstCommunityDTO;
import com.wj.core.entity.base.embeddable.FamilyCommuntity;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.SysUserInfoDTO;
import com.wj.core.repository.base.*;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.QstCommuntityService;
import com.wj.core.service.qst.dto.QstResult;
import com.wj.core.service.qst.dto.TenantstructuresIssuseDTO;
import com.wj.core.service.qst.dto.TenantvillagesDTO;
import com.wj.core.util.CommonUtils;
import com.wj.core.util.base.CommunityUtil;
import com.wj.core.util.mapper.JsonMapper;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private BaseFamilyRepository baseFamilyRepository;
    @Autowired
    private QstCommuntityService qstCommuntityService;
    @Autowired
    private BaseStoreyRepository baseStoreyRepository;

    static JsonMapper mapper = JsonMapper.defaultMapper();


    /**
     * 根据id查询社区信息
     *
     * @param id
     * @return BaseCommuntity
     */
    public BaseCommuntity findById(Integer id) {
        BaseCommuntity baseCommuntity = baseCommuntityRepository.findByCommuntityId(id);
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
            communtity.setCreateDate(new Date());
            communtity = baseCommuntityRepository.save(communtity);
            String code = CommunityUtil.genCommCode(communtity.getId());
            baseCommuntityRepository.modityCode(code, communtity.getId());
            communtity.setCode(code);
            Map<String, Object> map = qstCommuntityService.tenantvillages(communtity.getArea().toString(), communtity.getName());
            communtity.setStructureId(Integer.valueOf(map.get("StructureID").toString()));
            communtity.setVillageName(map.get("VillageName").toString());
            communtity.setDirectory(map.get("Directory").toString());
            Map<String, Object> result = qstCommuntityService.tenantStructureDefinition(communtity.getStructureId());
            if (Integer.valueOf(result.get("Code").toString()) != 201) {
                throw new ServiceException("同步全视通数据错误", ErrorCode.QST_ERROR);
            }
            return communtity;
        } else {
            BaseCommuntity bc = baseCommuntityRepository.getOne(communtity.getId());
            communtity.setFlag(bc.getFlag());
            communtity.setCode(bc.getCode());
            communtity.setCreateDate(bc.getCreateDate());
            baseCommuntityRepository.save(communtity);
            return communtity;
        }
    }

    @Transactional
    public BaseCommuntity saveQstCommuntity(QstCommunityDTO communityDTO) {
        BaseCommuntity communtity = new BaseCommuntity();
        communtity.setName(communityDTO.getName());
        communtity.setCreateDate(new Date());
        baseCommuntityRepository.modityCode(CommunityUtil.genCommCode(communtity.getId()), communtity.getId());
        Map<String, Object> map = qstCommuntityService.tenantvillages(communityDTO.getArea().toString(), communityDTO.getName());
        communtity.setStructureId(Integer.valueOf(map.get("StructureID").toString()));
        communtity.setVillageName(map.get("VillageName").toString());
        communtity.setDirectory(map.get("Directory").toString());
        Map<String, Object> result = qstCommuntityService.tenantStructureDefinition(communtity.getStructureId(), communityDTO.getPeriod(), communityDTO.getRegion(), communityDTO.getBuilding(), communityDTO.getUnit(), communityDTO.getFloor(), communityDTO.getRoom());
        communtity.setFlag(result.get("flag").toString());
        baseCommuntityRepository.save(communtity);
        if (Integer.valueOf(result.get("Code").toString()) != 201) {
            throw new ServiceException("同步全视通数据错误", ErrorCode.QST_ERROR);
        }
        return communtity;
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

    /**
     * 根据社区查询当前社区所有家庭
     *
     * @param communtityCode
     * @return List
     */
    public List<BaseFamilyDTO> findFamilyListByCode(String communtityCode) {
        // 根据社区查询所有期
        List<BaseFamilyDTO> list = Lists.newArrayList();
        BaseCommuntity bc = baseCommuntityRepository.findByCode(communtityCode);
        String flag = bc.getFlag();
        List<BaseUnit> baseUnits = baseUnitRepository.findByCodeLike(communtityCode + "%");
        for (BaseUnit bu : baseUnits) {
            BaseFamilyDTO baseFamilyDTO = new BaseFamilyDTO();
            String buCode = bu.getCode();
            String name = "";
            if (StringUtils.contains(flag, "期")) {
                BaseIssue bi = baseIssueRepository.findByCode(buCode.substring(0, 10));
                name += bi.getName();
            }
            if (StringUtils.contains(flag, "区")) {
                BaseDistrict bd = baseDistrictRepository.findByCode(buCode.substring(0, 12));
                name += bd.getName();
            }
            if (StringUtils.contains(flag, "楼")) {
                BaseFloor bf = baseFloorRepository.findByCode(buCode.substring(0, 14));
                name += bf.getName();
            }
            name += bu.getUnitNo();
            baseFamilyDTO.setName(name);
            baseFamilyDTO.setList(baseFamilyRepository.findByCodeLike(bu.getCode() + "%"));
            list.add(baseFamilyDTO);
        }
        return list;
    }


    public Page<BaseCommuntity> findByCityCodeAndName(Integer cityCode, String name, Pageable pageable) {
        Page<BaseCommuntity> page = null;
        if (cityCode != null && !CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByCityCodeAndName(cityCode, name, pageable);
        } else if (cityCode != null && CommonUtils.isNull(name)) {
            page = baseCommuntityRepository.findByCityCode(cityCode, pageable);
        } else if (cityCode == null && !CommonUtils.isNull(name)) {
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

}
