package com.wj.core.service.base;

import com.google.common.collect.Lists;
import com.wj.core.entity.base.*;
import com.wj.core.entity.base.dto.BaseFamilyDTO;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.SysUserInfoDTO;
import com.wj.core.repository.base.*;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.CommonUtils;
import com.wj.core.util.base.CommunityUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BaseCommuntityInfoService {

    @Autowired
    private BaseCommuntityInfoRepository baseCommuntityInfoRepository;


    public Page<BaseCommuntityInfo> findAll(Integer communtityId, Pageable pageable) {
        return baseCommuntityInfoRepository.findAll(communtityId, pageable);
    }

    public List<BaseCommuntityInfo> findList(Integer communtityId) {
        return baseCommuntityInfoRepository.findList(communtityId);
    }
}
