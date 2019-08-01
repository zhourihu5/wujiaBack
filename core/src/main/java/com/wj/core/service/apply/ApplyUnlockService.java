package com.wj.core.service.apply;

import com.wj.core.entity.address.Address;
import com.wj.core.entity.apply.ApplyUnlock;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.apply.ApplyUnlockRepository;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.FamilyCommuntityRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplyUnlockService {

    @Autowired
    private ApplyUnlockRepository applyUnlockRepository;

    @Transactional
    public void saveApplyUnlock(ApplyUnlock applyUnlock) {
        applyUnlock.setStatus("0");
        applyUnlockRepository.save(applyUnlock);
    }
}
