package com.wj.core.service.message;

import com.google.common.collect.Lists;
import com.wj.core.entity.message.Message;
import com.wj.core.entity.message.enums.MessageStatus;
import com.wj.core.entity.message.enums.MessageType;
import com.wj.core.repository.message.MessageRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public Page<Message> getList(String token, Integer pageNo, Integer type, Integer status) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            // 99 代表所有
            if (type != null && type < 99) {
                if (type == MessageType.SQ.ordinal()) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), MessageType.SQ));
                }
                if (type == MessageType.SY.ordinal()) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), MessageType.SY));
                }
                if (type == MessageType.WY.ordinal()) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), MessageType.WY));
                }
            }
            if (status != null && status < 99) {
                if (status == MessageStatus.NO.ordinal()) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), MessageStatus.NO));
                }
                if (status == MessageStatus.YES.ordinal()) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), MessageStatus.YES));
                }
            }
            predicates.add(criteriaBuilder.equal(root.get("userId"), 1));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNo == null) {
            pageNo = 1;
        }
        Pageable page = PageRequest.of(pageNo - 1, 10, Sort.Direction.ASC, "createDate");
        return messageRepository.findAll(specification, page);
    }

    @Transactional
    public void modifyRead(String token, @NotBlank(message = "消息ID为空") Integer id) {

        if (id == null) {
            throw new ServiceException("消息ID未空", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        messageRepository.modityStatus(String.valueOf(MessageStatus.YES.ordinal()), id, 1);
    }

    public List<Message> getTop3UnReadMessage(String token) {
        return messageRepository.findTop3ByUserIdAndStatusOrderByCreateDateDesc(1, MessageStatus.NO);
    }

    public boolean isUnReadMessage(String token) {
        int count = messageRepository.countByUserIdAndStatus(1, MessageStatus.NO);
        if (count > 0) {
            return true;
        }
        return false;
    }

}
