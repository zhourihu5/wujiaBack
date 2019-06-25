package com.wj.core.service.op;

import com.wj.core.entity.op.OpBanner;
import com.wj.core.repository.op.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void saveBanner(OpBanner banner) {
        banner.setCreateDate(new Date());
        bannerRepository.save(banner);
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

}
