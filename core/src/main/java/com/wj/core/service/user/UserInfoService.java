package com.wj.core.service.user;

import com.google.common.collect.Lists;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.UserInfoDTO;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.CommonUtils;
import com.wj.core.util.mapper.BeanMapper;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BaseFamilyRepository familyRepository;

    @Autowired
    private UserFamilyRepository userFamilyRepository;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据名字查询用户信息
     *
     * @param name
     * @return SysUserInfo
     */
    public SysUserInfo findByName(String name) {
        return userInfoRepository.findByName(name);
    }

    /**
     * 根据用户名和密码查询用户是否存在
     *
     * @param name
     * @param pwd
     * @return Integer
     */
    public SysUserInfo findByNameAndPwd(String name, String pwd) {
        return userInfoRepository.findByNameAndPwd(name, pwd);
    }

    /**
     * 选择用户并将用户添加到家庭中
     *
     * @param uid
     * @param fid
     * @return void
     */
    @Transactional
    public void save(Integer uid, Integer fid) {
        BaseFamily f = familyRepository.getOne(fid);
        SysUserInfo sysUserInfo = userInfoRepository.getOne(uid);
        List<BaseFamily> familyList = sysUserInfo.getFamilyId();
        familyList.add(f);
        sysUserInfo.setFamilyId(familyList);
        userInfoRepository.save(sysUserInfo);
    }

    /**
     * 获取用户详细信息
     *
     * @param uid
     * @return void
     */
    @Transactional
    public SysUserInfo findUserInfo(Integer uid) {
        return userInfoRepository.findByUserId(uid);
    }

    /**
     * 新增用户信息并关联家庭表
     *
     * @param userName
     * @param fid
     * @return void
     */
    @Transactional
    public Integer saveUserAndFamily(String userName, Integer fid) {
        SysUserInfo userInfo = userInfoRepository.findByName(userName);
        if (null != userInfo) {
            return 1;
        }
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserName(userName);
        sysUserInfo.setCreateDate(new Date());
        sysUserInfo = userInfoRepository.save(sysUserInfo);
        System.out.println(sysUserInfo.getId());
        // 关联家庭
        UserFamily userFamily = new UserFamily();
        userFamily.setFamilyId(fid);
        userFamily.setUserId(sysUserInfo.getId());
        SysUserFamily sysUserFamily = new SysUserFamily();
        sysUserFamily.setUserFamily(userFamily);
        sysUserFamily.setIdentity(1);
        userFamilyRepository.save(sysUserFamily);
        return 0;
    }

    /**
     * 获取用户详细信息
     *
     * @param userName
     * @param nickName
     * @return void
     */
    public Page<SysUserInfo> findAll(String userName, String nickName, Pageable pageable) {
        Page<SysUserInfo> page = null;
        if (!CommonUtils.isNull(userName) && !CommonUtils.isNull(nickName)) {
            page = userInfoRepository.findByUserNameAndNickName(userName, nickName, pageable);
        } else if (!CommonUtils.isNull(userName) && CommonUtils.isNull(nickName)) {
            page = userInfoRepository.findByUserName(userName, pageable);
        } else if (CommonUtils.isNull(userName) && !CommonUtils.isNull(nickName)) {
            page = userInfoRepository.findByNickName(nickName, pageable);
        } else {
            page = userInfoRepository.findAll(pageable);
        }
        //根据用户id查询家庭
        //根据家庭id查询社区
        return page;
    }

    /**
     * 新增/修改用户
     *
     * @param sysUserInfo
     * @return void
     */
    @Transactional
    public void saveUser(SysUserInfo sysUserInfo) {
        SysUserInfo userInfo = userInfoService.findByName(sysUserInfo.getUserName());
        if (userInfo != null) {
            throw new ServiceException("此账号已经存在", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        sysUserInfo.setCreateDate(new Date());
        userInfoRepository.save(sysUserInfo);
    }

    @Transactional
    public SysUserInfo addUser(SysUserInfo sysUserInfo) {
        sysUserInfo.setCreateDate(new Date());
        return userInfoRepository.save(sysUserInfo);
    }

    /**
     * 删除用户
     *
     * @param sysUserInfo
     * @return void
     */
    @Transactional
    public void delUser(SysUserInfo sysUserInfo) {
        userInfoRepository.delete(sysUserInfo);
    }

}

