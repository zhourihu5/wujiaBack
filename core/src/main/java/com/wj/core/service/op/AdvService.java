package com.wj.core.service.op;

import com.wj.core.entity.enums.ServiceType;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpFamilyService;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.op.embeddable.FamilyService;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.ServiceAllDTO;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.op.*;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.base.BaseCommuntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdvService {

    @Autowired
    private ServeRepository serviceRepository;

    @Autowired
    private BaseFamilyRepository baseFamilyRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private FamilyServeRepository familyServeRepository;

    @Autowired
    private AdvUserRepository advUserRepository;

    @Autowired
    private AdvCommuntityRepository advCommuntityRepository;

    @Autowired
    private AdvRepository advRepository;

    @Autowired
    private BaseCommuntityService baseCommuntityService;


    /**
     * 根据id查询广告详情
     *
     * @param id
     * @return OpAdv
     */
    public OpAdv findById(Integer id) {
        return advRepository.getOne(id);
    }


    /**
     * 添加/更新广告
     *
     * @param adv
     * @return void
     */
    @Transactional
    public void saveService(OpAdv adv) {
        adv.setCreateDate(new Date());
        advRepository.save(adv);
    }

    /**
     * 广告分页列表
     *
     * @param pageable
     * @return Page<OpAdv>
     */
    public Page<OpAdv> findAll(Pageable pageable) {
        return advRepository.findAll(pageable);
    }


    /**
     * 向用户推送广告
     *
     * @param advId
     * @param communtity
     * @return void
     */
    @Transactional
    public void pushAdv(Integer advId, String communtity) {
        String[] strArray = communtity.split(",");
        for (int i = 0; i < strArray.length; i++) {
            // 保存广告和社区关系
            advCommuntityRepository.addAdvCommuntity(advId, Integer.valueOf(strArray[i]), new Date());
            List<SysUserInfo> list = baseCommuntityService.findUserListByCid(Integer.valueOf(strArray[i]));
            list.forEach(SysUserInfo -> {
                // 保存广告和用户关系
                advUserRepository.addAdvUser(advId, SysUserInfo.getId(), new Date());
            });
        }
    }
}
