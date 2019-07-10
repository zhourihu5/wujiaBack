package com.wj.core.service.message;

import com.google.common.collect.Lists;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.SysMessageUser;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.SysUserInfoDTO;
import com.wj.core.repository.message.MessageCommuntityRepository;
import com.wj.core.repository.message.MessageRepository;
import com.wj.core.repository.message.MessageUserRepository;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.util.jiguang.JPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageCommuntityRepository messageCommuntityRepository;

    @Autowired
    private MessageUserRepository messageUserRepository;

    @Autowired
    private BaseCommuntityService baseCommuntityService;

    @Transactional
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Transactional
    public void saveMessageUser(SysMessageUser messageUser) {
        messageUserRepository.save(messageUser);
    }

    public Page<Message> findListByUserId(Integer userId, Integer familyId, Integer isRead, Integer type, Pageable pageable) {
        Page<Message> page = null;
        if (type != null && isRead != null) {
            page = messageRepository.findByUserIdAndIsRead(userId, familyId, isRead, type, pageable);
        } else if (type != null && isRead == null) {
            page = messageRepository.findByUserId(userId, familyId, type, pageable);
        } else if (type == null && isRead != null) {
            page = messageRepository.findByUserIdAndIsRead(userId, familyId, isRead, pageable);
        } else {
            page = messageRepository.findByUserId(userId, familyId, pageable);
        }
        for (Message message : page) {
            Integer status = messageUserRepository.findByMessageAndUser(message.getId(), userId, familyId);
            if (status == null) {
                message.setIsRead(0);
            } else {
                message.setIsRead(status);
            }
        }
        return page;
    }

    public List<Message> findTopThreeByUserId(Integer userId, Integer familyId, Integer isRead) {
        return messageRepository.findTopThreeByUserId(userId, familyId, isRead);
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
     *
     * @param title
     * @return Page<Message>
     */
    public Page<Message> findAll(String title, Pageable pageable) {
        Page<Message> page = null;
        if (title != null) {
            page = messageRepository.findByTitle(title, pageable);
        } else {
            page = messageRepository.findAll(pageable);
        }
        return page;
    }

    /**
     * 向用户推送消息
     *
     * @param messageId
     * @param communtity
     * @return void
     */
    @Transactional
    public void pushMessage(Integer messageId, String communtity) {
        String[] strArray = communtity.split(",");
        List<String> tagList = Lists.newArrayList();
        for (int i = 0; i < strArray.length; i++) {
            // 保存消息和社区关系
            tagList.add("community_" + strArray[i]);
            messageCommuntityRepository.addMessageCommuntity(messageId, Integer.valueOf(strArray[i]), new Date());
            List<SysUserInfoDTO> list = baseCommuntityService.findUserListByCid(Integer.valueOf(strArray[i]));
            list.forEach(SysUserInfo -> {
                // 保存消息和用户关系R
                messageUserRepository.addMessageUser(messageId, SysUserInfo.getId(), SysUserInfo.getFid(), 0, new Date());
            });
            // 消息推送
            JPush.sendMsgPush(tagList);
        }
    }

}
