package com.wj.core.service.user;

import com.wj.core.entity.user.SysScreen;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.user.ScreenRepository;
import com.wj.core.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

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
    public List<SysScreen> findAll() {
        return screenRepository.findAll();
    }



}
