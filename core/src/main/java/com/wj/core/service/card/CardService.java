package com.wj.core.service.card;

import com.google.common.collect.Lists;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.dto.CardDTO;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.repository.card.CardRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.mapper.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    // 获取用户卡片
    public List<OpCard> getUserCard(String token) {
        return cardRepository.findByUserIdAndIsShow(1, CardStatus.YES);
    }

    // 获取所有卡片
    public List<OpCard> getCardList(String token) {
        return cardRepository.findByUserId(1);
    }

    // 保存用户卡片
    @Transactional
    public void saveUserCard(String token, Integer id) {
        List<CardDTO> list = Lists.newArrayList();
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("token为空", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        cardRepository.modityCardStatus(CardStatus.YES, 1, id);
    }

    // 删除用户卡片
    @Transactional
    public void removeUserCard(String token, Integer cardId) {
        cardRepository.modityCardStatus(CardStatus.NO, 1, cardId);
    }

}
