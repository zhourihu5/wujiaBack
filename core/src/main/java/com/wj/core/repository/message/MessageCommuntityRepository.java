package com.wj.core.repository.message;

import com.wj.core.entity.message.SysMessageCommuntity;
import com.wj.core.entity.message.SysMessageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface MessageCommuntityRepository extends JpaRepository<SysMessageCommuntity, Integer> {

    @Query(value = "select * from sys_message_communtity where message_id = ?1", nativeQuery = true)
    public List<SysMessageCommuntity> findCommuntityByMessageId(Integer messageId);

    @Query(value = "select count(*) from sys_message_communtity where message_id = ?1", nativeQuery = true)
    public Integer findCountByMessageId(Integer messageId);

    @Modifying
    @Query(value = "insert into sys_message_communtity(message_id, communtity_id, create_date) values (?1,?2,?3)", nativeQuery = true)
    public Integer addMessageCommuntity(Integer messageId, Integer communtityId, Date date);
}
