package com.wj.core.service.base;

import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.base.BaseFloor;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.BaseFloorRepository;
import com.wj.core.repository.base.BaseUnitRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BaseFloorService {

    @Autowired
    private BaseFloorRepository baseFloorRepository;

    @Autowired
    private BaseUnitRepository baseUnitRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    /**
     * 保存楼信息
     *
     * @param floor
     * @return void
     */
    @Transactional
    public void saveFloor(BaseFloor floor) {
        if (floor.getId() == null) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(floor.getCode().substring(0, 16));
            System.out.println("---------" + floor.getCode().substring(0, 16));
            Integer count = 0;
            if (floor.getCommuntityId() != null) {
                count = baseFloorRepository.findCountByCommuntityId(floor.getCommuntityId());
            } else if (floor.getIssueId() != null){
                count = baseFloorRepository.findCountByIssueId(floor.getIssueId());
            } else if (floor.getDirectory() != null){
                count = baseFloorRepository.findCountByDistrictId(floor.getDistrictId());
            }
            String number = "";
            if (count == null || count == 0) {
                number = "01";
            } else if (count > 0 && count < 10) {
                number = "0" + (count + 1);
            } else if (count > 10) {
                number = "" + (count + 1);
            }
            sBuffer.append(number);
            sBuffer.append("0000000000");
            System.out.println("sBuffer++++++++++++++" + sBuffer);
            floor.setCode(sBuffer.toString());
        }
        floor.setCreateDate(new Date());
        baseFloorRepository.save(floor);
    }

    /**
     * 获取楼分页信息
     *
     * @param communtityId
     * @return Page<BaseFloor>
     */
    public Page<BaseFloor> findAll(Integer communtityId, Pageable pageable) {
        Page<BaseFloor> page = null;
        if (communtityId != null) {
            page = baseFloorRepository.findByCommuntityId(communtityId, pageable);
        } else {
            page = baseFloorRepository.findAll(pageable);
        }
        for (BaseFloor baseFloor : page) {
            BaseCommuntity communtity = baseCommuntityRepository.findByCommuntityId(baseFloor.getCommuntityId());
            if (communtity == null)
                throw new ServiceException("社区数据异常", ErrorCode.INTERNAL_SERVER_ERROR);
            baseFloor.setCommuntityName(communtity.getName());
        }
        return page;
    }

    public List<BaseFloor> findByCommuntityId(Integer communtityId) {
        return baseFloorRepository.findByCommuntityId(communtityId);
    }
}
