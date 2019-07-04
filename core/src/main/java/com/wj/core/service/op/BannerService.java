package com.wj.core.service.op;

import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpBannerType;
import com.wj.core.repository.op.BannerRepository;
import com.wj.core.repository.op.BannerTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerTypeRepository bannerTypeRepository;

    @Value("${wj.oss.access}")
    private String url;

    public void saveBanner(OpBanner banner) {
        banner.setCreateDate(new Date());
        if (banner.getId() != null) {
            if (StringUtils.isNotBlank(banner.getCover()) && !banner.getCover().startsWith("http")) {
                banner.setCover(url + banner.getCover());
            }
        } else {
            if (StringUtils.isNotBlank(banner.getCover())) {
                banner.setCover(url + banner.getCover());
            }
        }
        bannerRepository.save(banner);
    }

    public void delBanner(OpBanner banner) {
        bannerRepository.delete(banner);
    }


    public List<OpBanner> findByModuleTypeList(Integer type) {
        return bannerRepository.findByModuleTypeList(type);
    }

    public Page<OpBanner> findAll(Integer type, Pageable pageable) {
        Page<OpBanner> page = null;
        if (type != null) {
            page = bannerRepository.findByType(type, pageable);
        } else {
            bannerRepository.findAll(pageable);
        }
        return page;
    }


    public List<OpBannerType> findBannerTypeList() {
        return bannerTypeRepository.findAll();
    }

}
