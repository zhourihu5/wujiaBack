package com.wj.core.service.apply;

import com.wj.core.entity.address.Address;
import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.repository.apply.ApplyUnlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ApplyLockService {

    @Autowired
    private ApplyUnlockRepository applyUnlockRepository;

    @Transactional
    public void saveApplyLock(ApplyLock applyLock) {
        applyLock.setStatus("0");
        applyUnlockRepository.save(applyLock);
    }


    public List<ApplyLock> findByUserId(Integer userId) {
        return applyUnlockRepository.findByUserId(userId);
    }
}
