package com.wj.core.repository.card;


import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.enums.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<OpCard, Integer> {

    List<OpCard> findByUserIdAndIsShow(Integer userId, CardStatus status);
    List<OpCard> findByUserId(Integer userId);
    Long deleteByUserIdAndId(Integer userId, Integer id);
}
