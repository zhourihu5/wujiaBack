package com.wj.core.service.message;

import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.SysMessageCommuntity;
import com.wj.core.entity.message.SysMessageUser;
import com.wj.core.repository.message.MessageCommuntityRepository;
import com.wj.core.repository.message.MessageRepository;
import com.wj.core.repository.message.MessageUserRepository;
import com.wj.core.service.base.BaseCommuntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageCommuntityRepository messageCommuntityRepository;

    @Autowired
    private MessageUserRepository messageUserRepository;


    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void saveMessageUser(SysMessageUser messageUser) {
        messageUserRepository.save(messageUser);
    }


    public Page<Message> findListByUserId(Integer userId, Integer isRead, Integer type, Pageable pageable) {
        Page<Message> page = null;
        if (type != null && isRead != null) {
            page = messageRepository.findByUserIdAndIsRead(userId, isRead, type, pageable);
        } else if (type != null && isRead == null) {
            page = messageRepository.findByUserId(userId, type, pageable);
        } else if (type == null && isRead != null) {
            page = messageRepository.findByUserIdAndIsRead(userId, isRead, pageable);
        } else {
            page = messageRepository.findByUserId(userId, pageable);
        }
        for (Message message: page) {
            Integer status = messageUserRepository.findByMessageAndUser(message.getId(), userId);
            if (status == null) {
                message.setIsRead(0);
            } else {
                message.setIsRead(status);
            }
        }
        return page;
    }

    public List<Message> findTopThreeByUserId(Integer userId, Integer isRead) {
        return messageRepository.findTopThreeByUserId(userId, isRead);
    }

    @Transactional
    public Integer updateIsRead(Integer messageId, Integer userId, Integer isRead) {
        return messageUserRepository.updateIsRead(messageId, userId, isRead);
    }

    public Boolean isUnReadMessage(Integer userId, Integer isRead) {
        Boolean flag = false;
        Integer count = messageUserRepository.isUnReadMessage(userId, isRead);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 消息分页列表
     * @param title
     * @return Page<Message>
     */
    public Page<Message> findAll(String title, Pageable pageable) {
        Page<Message> page = null;
        if (title != null) {
            page = messageRepository.findByTitle(title ,pageable);
        } else {
            page = messageRepository.findAll(pageable);
        }
        return page;
    }

    /**
     * 向用户推送消息
     * @param
     * @return
     */
//    public void pushMessage(SysMessageCommuntity messageCommuntity) {
//        Integer messageId = 0;
//        List<SysMessageCommuntity> messageCommuntityList = messageCommuntityRepository.findCommuntityByMessageId(messageId);
//        messageCommuntityList.forEach(SysMessageCommuntity -> {
//
//        });
//
//        messageUserRepository.save(messageUser);
//    }

}
