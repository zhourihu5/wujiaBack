package com.wj.core.service.message;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.SysMessageUser;
import com.wj.core.entity.message.dto.MessageTypeDTO;
import com.wj.core.entity.message.enums.MessageType;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.SysUserInfoDTO;
import com.wj.core.entity.user.embeddable.UserFamily;
import com.wj.core.repository.message.MessageCommuntityRepository;
import com.wj.core.repository.message.MessageRepository;
import com.wj.core.repository.message.MessageUserRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import com.wj.core.service.base.BaseCommuntityService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.jiguang.JPush;
import com.wj.core.util.time.ClockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
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
    @Autowired
    private UserFamilyRepository userFamilyRepository;

    @Transactional
    public Message saveMessage(Message message) {
        message.setCreateDate(ClockUtil.currentDate());
        message.setStatus(0);
        return messageRepository.save(message);
    }

    @Transactional
    public void saveMessageUser(SysMessageUser messageUser) {
        messageUserRepository.save(messageUser);
    }

    @Transactional
    public void addMessageUser(Integer messageId, Integer userId, Integer familyId, Integer status) {
        messageUserRepository.addMessageUser(messageId, userId, familyId, status, new Date());
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
     * ??????????????????
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
     * ?????????????????????
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
            // ???????????????????????????
            tagList.add("community_" + strArray[i]);
            messageCommuntityRepository.addMessageCommuntity(messageId, Integer.valueOf(strArray[i]), new Date());
            List<SysUserInfoDTO> list = baseCommuntityService.findUserListByCid(Integer.valueOf(strArray[i]));
            list.forEach(SysUserInfo -> {
                // ???????????????????????????R
                messageUserRepository.addMessageUser(messageId, SysUserInfo.getId(), SysUserInfo.getFid(), 0, new Date());
            });
            // ????????????
            JPush.sendMsgPush(tagList);
        }
    }

    public List<MessageTypeDTO> getTypeList(Integer userId) {
        List<SysUserFamily> familyList = userFamilyRepository.findByUserId(userId);
        if (CollectionUtils.isEmpty(familyList)) {
            throw new ServiceException("?????????????????????", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        //TODO ????????????????????????
        Integer familyId = familyList.get(0).getUserFamily().getFamilyId();
        MessageTypeDTO sysMsg = new MessageTypeDTO();
        sysMsg.setTypeNo("3");
        sysMsg.setTypeName("????????????");
        sysMsg.setIcon("https://wujia01.oss-cn-beijing.aliyuncs.com/msg_system.png");
        List<Message> messageList3 = messageRepository.findUserTypeMsg(userId, familyId, 3, "0");
        sysMsg.setUnReadNum(messageList3.size());
        sysMsg.setUnReadList(messageList3);
        MessageTypeDTO comMsg = new MessageTypeDTO();
        comMsg.setTypeNo("2");
        comMsg.setTypeName("????????????");
        List<Message> messageList2 = messageRepository.findUserTypeMsg(userId, familyId, 2, "0");
        comMsg.setUnReadNum(messageList2.size());
        comMsg.setUnReadList(messageList2);
        comMsg.setIcon("https://wujia01.oss-cn-beijing.aliyuncs.com/msg_community.png");
        MessageTypeDTO orderMsg = new MessageTypeDTO();
        orderMsg.setTypeNo("4");
        orderMsg.setTypeName("????????????");
        orderMsg.setIcon("https://wujia01.oss-cn-beijing.aliyuncs.com/msg_order.png");
        List<Message> messageList4 = messageRepository.findUserTypeMsg(userId, familyId, 4, "0");
        orderMsg.setUnReadNum(messageList4.size());
        orderMsg.setUnReadList(messageList4);
        List<MessageTypeDTO> list = Lists.newArrayList(sysMsg, comMsg, orderMsg);
        return list;
    }

}
