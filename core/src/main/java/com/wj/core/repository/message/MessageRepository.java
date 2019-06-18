package com.wj.core.repository.message;

import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

    @Modifying
    @Query(value = "update sys_message m set m.status = ?1 where m.id = ?2 and m.user_id = ?3", nativeQuery = true)
    void modityStatus(String status, Integer id, Integer userId);

    List<Message> findTop3ByUserIdAndStatusOrderByCreateDateDesc(Integer userId, MessageStatus status);

    int countByUserIdAndStatus(Integer userId, MessageStatus status);
}
