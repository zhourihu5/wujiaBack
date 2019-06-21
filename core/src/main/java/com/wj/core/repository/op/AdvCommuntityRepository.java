package com.wj.core.repository.op;

import com.wj.core.entity.op.OpAdvCommuntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;


public interface AdvCommuntityRepository extends JpaRepository<OpAdvCommuntity, Integer> {

    @Modifying
    @Query(value = "insert into op_adv_communtity(adv_id, communtity_id, create_date) values(?1,?2,?3)", nativeQuery = true)
    public Integer addAdvCommuntity(Integer advId, Integer communtityId, Date date);
    
}
