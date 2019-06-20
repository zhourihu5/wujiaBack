package com.wj.core.repository.message;

import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.SysMessageUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface MessageUserRepository extends JpaRepository<SysMessageUser, Integer> {

    @Modifying
    @Query(value = "update sys_message_user set is_read = ?3 where message_id = ?1 and user_id = ?2", nativeQuery = true)
    public Integer updateIsRead(Integer messageId, Integer userId, Integer isRead);

    @Query(value = "select is_read from sys_message_user where message_id = ?1 and user_id = ?2", nativeQuery = true)
    public Integer findByMessageAndUser(Integer messageId, Integer userId);
}
