package com.wj.core.repository.card;


import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.enums.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<OpCard, Integer> {

    List<OpCard> findByUserCards_UserInfo_IdAndUserCards_IsShow(Integer userId, CardStatus status);
    List<OpCard> findByUserCards_UserInfo_Id(Integer userId);
    //List<OpCard> findByUserCardsUserId(Integer userId, CardStatus status);
    //List<OpCard> findByUserId(Integer userId);
   // Long deleteByUserIdAndId(Integer userId, Integer id);

    @Modifying
    @Query(value = "update op_user_card o set o.is_show = ?1 where o.user_id = ?2 and o.card_id = ?3", nativeQuery = true)
    void modityCardStatus(Integer status, Integer userId, Integer id);
}
