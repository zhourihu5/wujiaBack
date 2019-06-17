package com.wj.core.service.op;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.enums.AllServiceType;
import com.wj.core.entity.enums.FindType;
import com.wj.core.entity.enums.GovernmentType;
import com.wj.core.entity.enums.ServiceType;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpFamilyService;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.op.embeddable.FamilyService;
import com.wj.core.entity.user.SysUserInfo;
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
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 根据id查询服务详情
     * @param moduleId
     * @return List<OpBanner>
     */
    public List<OpBanner> findByModuleIdList(Integer moduleId) {
        return bannerRepository.findByModuleIdList(moduleId);
    }



}
