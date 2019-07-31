package com.wj.core.repository.atta;

import com.wj.core.entity.atta.AttaInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttaInfoRepository extends JpaRepository<AttaInfo, Integer> {

    List<AttaInfo> findByObjectIdAndObjectType(Integer objId, String objType);

    void deleteByObjectIdAndObjectType(Integer objId, String objType);
}
