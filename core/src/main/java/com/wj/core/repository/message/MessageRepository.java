package com.wj.core.repository.message;

import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.enums.MessageStatus;
import com.wj.core.entity.op.OpService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

    @Query(value = "select * from sys_message a,sys_message_user b where a.id = b.message_id and b.user_id = ?1", nativeQuery = true)
    public Page<Message> findByUserId(Integer userId, Pageable pageable);

    @Query(value = "select * from sys_message a,sys_message_user b where a.id = b.message_id and b.user_id = ?1 and a.type = ?2", nativeQuery = true)
    public Page<Message> findByUserId(Integer userId, Integer type, Pageable pageable);

    @Query(value = "select * from sys_message a,sys_message_user b where a.id = b.message_id and b.user_id = ?1 and b.is_read = ?2", nativeQuery = true)
    public Page<Message> findByUserIdAndIsRead(Integer userId, Integer isRead, Pageable pageable);

    @Query(value = "select * from sys_message a,sys_message_user b where a.id = b.message_id and b.user_id = ?1 and b.is_read = ?2 and a.type = ?3", nativeQuery = true)
    public Page<Message> findByUserIdAndIsRead(Integer userId, Integer isRead, Integer type, Pageable pageable);

    @Query(value = "select * from sys_message a,sys_message_user b where a.id = b.message_id and b.user_id = ?1 and b.is_read = ?2 order by create_date desc limit 3", nativeQuery = true)
    public List<Message> findTopThreeByUserId(Integer userId, Integer isRead);

    @Query(value = "select * from sys_message where title like CONCAT('%',?1,'%')", nativeQuery = true)
    public Page<Message> findByTitle(String title, Pageable pageable);
}
