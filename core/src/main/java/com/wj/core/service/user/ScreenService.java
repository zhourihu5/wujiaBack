package com.wj.core.service.user;

import com.wj.core.entity.user.SysScreen;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.user.ScreenRepository;
import com.wj.core.repository.user.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    @Value("${wj.oss.access}")
    private String url;

    /**
     * 根据id查询屏保信息
     * @param id
     * @return SysScreen
     */
    public SysScreen findById(int id) {
        return screenRepository.findById(id);
    }

    /**
     * 根据社区id查询屏保信息
     * @param communtityId
     * @return SysScreen
     */
    public List<SysScreen> findByCommuntityId(int communtityId) {
        return screenRepository.findByCommuntityId(communtityId);
    }

    /**
     * 查询屏保
     * @param
     * @return SysScreen
     */
    public Page<SysScreen> findAll(Pageable pageable) {
        return screenRepository.findAll(pageable);
    }

    public SysScreen getScreen() {
        return screenRepository.findFirstByOrderByIdDesc();
    }

    /**
     * 修改和保存屏保
     * @param screen
     * @return void
     */
    public void saveScreen(SysScreen screen) {
        screen.setCreateDate(new Date());
        if (StringUtils.isNotBlank(screen.getCover())) {
            screen.setCover(url + screen.getCover());
        }
        screenRepository.save(screen);
    }

}
