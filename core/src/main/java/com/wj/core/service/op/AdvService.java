package com.wj.core.service.op;

import com.google.common.collect.Lists;
import com.wj.core.entity.enums.ServiceType;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.op.OpAdv;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpFamilyService;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.op.dto.AdvertDTO;
import com.wj.core.entity.op.embeddable.FamilyService;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.ServiceAllDTO;
import com.wj.core.repository.base.BaseFamilyRepository;
import com.wj.core.repository.op.*;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.util.jiguang.JPush;
import com.wj.core.util.mapper.JsonMapper;
import com.wj.core.util.time.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.List;

import static com.wj.core.util.time.DateFormatUtil.*;

@Service
public class AdvService {


    @Autowired
    private AdvUserRepository advUserRepository;

    @Autowired
    private AdvCommuntityRepository advCommuntityRepository;

    @Autowired
    private AdvRepository advRepository;

    @Autowired
    private BaseCommuntityService baseCommuntityService;
    @Value("${wj.oss.access}")
    private String url;


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
    public OpAdv saveAdv(OpAdv adv) {
        adv.setCreateDate(new Date());
        if (StringUtils.isNotBlank(adv.getCover())) {
            adv.setCover(url + adv.getCover());
        }
        return advRepository.save(adv);
    }

    /**
     * 删除广告
     *
     * @param id
     * @return void
     */
    @Transactional
    public void delAdv(Integer id) {
        advCommuntityRepository.delAdvCommuntityByAdvId(id);
        advUserRepository.delAdvUserByAdvId(id);
        advRepository.deleteById(id);
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
        List tagList = Lists.newArrayList();
        String[] strArray = communtity.split(",");
        for (int i = 0; i < strArray.length; i++) {
            // 保存广告和社区关系
            tagList.add(strArray[i]);
            advCommuntityRepository.addAdvCommuntity(advId, Integer.valueOf(strArray[i]), new Date());
            List<SysUserInfo> list = baseCommuntityService.findUserListByCid(Integer.valueOf(strArray[i]));
            list.forEach(SysUserInfo -> {
                // 保存广告和用户关系
                advUserRepository.addAdvUser(advId, SysUserInfo.getId(), new Date());
            });
        }
        // 广告推送
        OpAdv adv = advRepository.getOne(advId);
        AdvertDTO advertDTO = new AdvertDTO();
        advertDTO.setUrl(adv.getCover());
        advertDTO.setHref(adv.getUrl());
        advertDTO.setId(adv.getId());
        advertDTO.setTitle(advertDTO.getTitle());
        advertDTO.setType("0");
        JsonMapper mapper = JsonMapper.defaultMapper();
        JPush.sendAdvSchedulePush(tagList, "adv_schedule_push_" + advId, formatDate(PATTERN_DEFAULT_ON_SECOND, adv.getStartDate()), formatDate(PATTERN_DEFAULT_ON_SECOND, adv.getEndDate()),adv.getDayTime(), mapper.toJson(adv));

    }
}
