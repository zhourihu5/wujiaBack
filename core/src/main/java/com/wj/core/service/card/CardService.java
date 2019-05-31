package com.wj.core.service.card;

import com.wj.core.entity.card.OpCard;
import com.wj.core.repository.card.CardRepository;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    // 获取用户卡片
    public List<OpCard> getUserCard(String token) {
        return null;
    }

    // 获取所有卡片
    public List<OpCard> getCardList() {
        return null;
    }

    // 保存用户卡片
    public void saveUserCard(String token, OpCard card) {
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("token为空", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        cardRepository.save(card);
    }

    // 删除用户卡片
    public void removeUserCard() {

    }

}
