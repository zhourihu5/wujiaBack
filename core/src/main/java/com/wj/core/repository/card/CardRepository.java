package com.wj.core.repository.card;


import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.enums.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<OpCard, Integer> {

    List<OpCard> findByUserIdAndIsShow(Integer userId, CardStatus status);
    List<OpCard> findByUserId(Integer userId);
    Long deleteByUserIdAndId(Integer userId, Integer id);

    @Modifying
    @Query("update OpCard o set o.isShow = ?1 where o.userId = ?2 and o.id = ?3")
    void modityCardStatus(CardStatus status, Integer userId, Integer id);
}
