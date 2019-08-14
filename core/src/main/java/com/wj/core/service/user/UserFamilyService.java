package com.wj.core.service.user;

import com.wj.core.entity.base.BaseDevice;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.base.BaseUnit;
import com.wj.core.entity.base.dto.FamilyBindInfoDTO;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.PadModule;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.entity.user.enums.UserIdentity;
import com.wj.core.repository.base.BaseDeviceRepository;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.repository.card.CardRepository;
import com.wj.core.repository.card.PadModuleRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.qst.QstBindingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserFamilyService {

    @Autowired
    private UserFamilyRepository userFamilyRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BaseDeviceRepository baseDeviceRepository;

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    @Autowired
    private QstBindingUserService qstBindingUserService;

    @Autowired
    private BaseUnitRepository baseUnitRepository;


    /**
     * 根据用户ID查询所属家庭
     *
     * @param userId
     * @return List<SysUserFamily>
     */
    public List<SysUserFamily> findByUserId(int userId) {
        return userFamilyRepository.findByUserId(userId);
    }

    /**
     * 根据家庭ID查询家庭成员列表
     *
     * @param familyId
     * @return List<SysUserFamily>
     */
    public List<SysUserFamily> findByFamilyId(int familyId) {
        return userFamilyRepository.findByFamilyId(familyId);
    }


    /**
     * 根据家庭ID获取家庭成员列表详情
     *
     * @param familyId
     * @return List<SysUserInfo>
     */
    public List<SysUserInfo> findFamilyToUser(Integer familyId) {
        List<SysUserFamily> userFamilyList = userFamilyRepository.findByFamilyId(familyId);
        List<SysUserInfo> sysUserInfoList = new ArrayList<>();
        for (SysUserFamily sysUserFamily : userFamilyList) {
            SysUserInfo sysUserInfo = userInfoRepository.findByUserId(sysUserFamily.getUserFamily().getUserId());
            if (sysUserInfo == null)  throw new ServiceException("用户数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            sysUserInfo.setIdentity(sysUserFamily.getIdentity());
            sysUserInfoList.add(sysUserInfo);
        }
        return sysUserInfoList;
    }

    /**
     * 根据用户id和家庭id查询家庭信息
     *
     * @param userId
     * @param familyId
     * @return SysUserFamily
     */
    public SysUserFamily findByUidAndFid(Integer userId, Integer familyId) {
        return userFamilyRepository.findByUidAndFid(userId, familyId);
    }

    /**
     * 绑定用户和家庭关系
     *
     * @param userFamily
     * @return void
     */
    @Transactional
    public void addUserAndFamily(SysUserFamily userFamily) {
        userFamilyRepository.deleteByUserFamily_FamilyIdAndIdentity(userFamily.getUserFamily().getFamilyId(), 1);
        if (UserIdentity.Owner.equals(userFamily.getIdentity())) {
            Integer count = userFamilyRepository.findByFamilyIdAndIdentity(userFamily.getUserFamily().getFamilyId(), userFamily.getIdentity());
            if (count > 0)
                throw new ServiceException("不能重复添加户主", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        // 查询所有卡片
        List<OpCard> list = cardRepository.findAll();
        list.forEach(OpCard -> {
            // 绑定用户和卡片关系
            Integer count = cardRepository.findUserCardCount(userFamily.getUserFamily().getUserId(), OpCard.getId());
            if (count == 0) {
                cardRepository.insertUserCard(userFamily.getUserFamily().getUserId(), OpCard.getId(), CardStatus.YES.ordinal());
            }
        });
        userFamilyRepository.save(userFamily);
        //全视通
        SysUserInfo userInfo = userInfoRepository.findByUserId(userFamily.getUserFamily().getUserId());
        BaseFamily baseFamily = baseFamilyRepository.findByFamilyId(userFamily.getUserFamily().getFamilyId());
//        String unitCode = baseFamily.getCode().substring(0, 16);
//        BaseUnit baseUnit = baseUnitRepository.findByUnitCode(unitCode);
        Map<String, Object> result = qstBindingUserService.userRooms(userInfo.getUserName(), baseFamily.getDirectory());
        if (Integer.valueOf(result.get("Code").toString()) != 200) {
            throw new ServiceException("同步全视通数据错误", ErrorCode.QST_ERROR);
        }
    }

    /**
     * 解绑用户和家庭关系
     *
     * @param userFamily
     * @return void
     */
    @Transactional
    public Integer delUserAndFamily(UserFamily userFamily) {
        return userFamilyRepository.delUserAndFamily(userFamily.getUserId(), userFamily.getFamilyId());
    }

    /**
     * 获取所有业主的用户ID
     * @param identity
     * @return List<SysUserFamily>
     */
    public List<SysUserFamily> findByIdentity(Integer identity) {
        return userFamilyRepository.findByIdentity(identity);
    }

    public FamilyBindInfoDTO getFamilyBindInfo(Integer familyId) {
        List<BaseDevice> list = baseDeviceRepository.findByFamilyId(familyId);
        FamilyBindInfoDTO fbd = new FamilyBindInfoDTO();
        for (BaseDevice bd : list) {
            if (bd.getFlag().intValue() == 1) {
                fbd.setPedestalDevice(bd);
            }
            if (bd.getFlag().intValue() == 2) {
                fbd.setPadDevice(bd);
            }
        }
        Integer userId = userFamilyRepository.getUserId(familyId, 1);
        SysUserInfo userInfo = userInfoRepository.findByUserId(userId);
        fbd.setUser(userInfo);
        return fbd;
    }




}
