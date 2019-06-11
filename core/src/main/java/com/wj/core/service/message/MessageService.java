package com.wj.core.service.message;

import com.google.common.collect.Lists;
import com.wj.core.entity.card.dto.CardDTO;
import com.wj.core.entity.message.dto.MessageDTO;
import com.wj.core.entity.message.enums.MessageStatus;
import com.wj.core.repository.message.MessageRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.mapper.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public Page<MessageDTO> getList(String token, Integer pageNo, String type, String status)  {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(type)) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (StringUtils.isNotBlank(status)) {
                predicates.add(criteriaBuilder.equal(root.get("status"), type));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNo == null) {
            pageNo = 1;
        }
        Pageable page = PageRequest.of(pageNo - 1, 10, Sort.Direction.ASC, "createDate");
        return messageRepository.findAll(specification, page);
    }

    public void updateStatus(Integer id) {
        if (id == null) {
            throw new ServiceException("消息ID未空", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        messageRepository.modityStatus(MessageStatus.YES, id);
    }

}
