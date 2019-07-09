package com.wj.core.repository.user;

import com.wj.core.entity.user.SysRestrict;
import com.wj.core.entity.user.SysScreen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestrictRepository extends JpaRepository<SysRestrict, Integer> {


    /**
     * 查询今天限行号码
     * @param startDate
     * @param endDate
     * @return SysScreen
     */
    @Query(value = "select * from sys_restrict where create_date >= ?1 and create_date <= ?2 and city_code = ?3", nativeQuery = true)
    public SysRestrict findByDate(String startDate, String endDate, Integer cityCode);




}
