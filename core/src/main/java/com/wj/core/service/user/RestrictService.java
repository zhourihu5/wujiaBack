package com.wj.core.service.user;

import com.wj.core.entity.user.SysRestrict;
import com.wj.core.entity.user.SysScreen;
import com.wj.core.repository.user.RestrictRepository;
import com.wj.core.repository.user.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class RestrictService {

    @Autowired
    private RestrictRepository restrictRepository;

    /**
     * 查询今天限行号码
     * @return SysRestrict
     */
    public SysRestrict findByDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        // 当前日期
        String startDate = sf.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 1);
        // 增加一天后日期
        String endDate = sf.format(c.getTime());
        return restrictRepository.findByDate(startDate, endDate);
    }

    /**
     * 查询今天限行号码
     * @return SysRestrict
     */
    @Transactional
    public void saveRestrict(SysRestrict restrict) {
        restrict.setCreateDate(new Date());
        restrictRepository.save(restrict);
    }





}
