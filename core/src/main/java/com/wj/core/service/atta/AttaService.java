package com.wj.core.service.atta;

import com.wj.core.entity.atta.AttaInfo;
import com.wj.core.repository.atta.AttaInfoRepository;
import com.wj.core.util.time.ClockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttaService {

    @Autowired
    private AttaInfoRepository attaInfoRepository;

    public void saveAttaInfo(AttaInfo attaInfo) {
        attaInfo.setCreateDate(ClockUtil.currentDate());
        attaInfoRepository.save(attaInfo);
    }

    public List<AttaInfo> getAttaList(Integer objId, String objType) {
        return attaInfoRepository.findByObjectIdAndObjectType(objId, objType);
    }

    public void removeAtta(Integer objId, String objType) {
        attaInfoRepository.deleteByObjectIdAndObjectType(objId, objType);
    }

}
