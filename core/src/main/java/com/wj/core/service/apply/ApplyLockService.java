package com.wj.core.service.apply;

import com.wj.core.entity.apply.ApplyLock;
import com.wj.core.repository.apply.ApplyUnlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ApplyLockService {

    @Autowired
    private ApplyUnlockRepository applyUnlockRepository;

    @Transactional
    public void saveApplyLock(ApplyLock applyLock) {
        applyLock.setStatus("0");
        applyUnlockRepository.save(applyLock);
    }
}
