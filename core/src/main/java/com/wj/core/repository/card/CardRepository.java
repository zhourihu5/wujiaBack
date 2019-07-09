package com.wj.core.repository.card;


import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<OpCard, Integer>, JpaSpecificationExecutor<OpCard> {

    List<OpCard> findByUserCards_UserInfo_IdAndUserCards_IsShowAndStatusOrderByLocationAsc(Integer userId, CardStatus isShow, CardStatus status);
    List<OpCard> findByUserCards_UserInfo_IdAndStatusOrderByLocationAsc(Integer userId, CardStatus status);
    OpCard findFirstByOrderByLocationDesc();
    List<OpCard> findByLocationIsGreaterThanEqual(Integer location);
    @Modifying
    @Query(value = "update op_user_card o set o.is_show = ?1 where o.user_id = ?2 and o.card_id = ?3", nativeQuery = true)
    void modityCardStatus(Integer status, Integer userId, Integer id);
    @Modifying
    @Query(value = "update OpCard o set o.location = ?1 where o.id = ?2")
    void modityLocation(Integer location, Integer id);

    @Modifying
    @Query(value = "update op_card o set o.status = ?1 where o.id = ?2", nativeQuery = true)
    void modityCardStatus(Integer status, Integer id);

    @Query(value = "select count(1) from op_user_card where user_id = ?1 and card_id = ?2", nativeQuery = true)
    int findUserCardCount(Integer userId, Integer cardId);

    @Modifying
    @Query(value = "insert into op_user_card(user_id, card_id, is_show) values (?1, ?2, ?3)", nativeQuery = true)
    void insertUserCard(Integer userId, Integer cardId, Integer isShow);

    @Query(value = "select * from op_card where id = ?1", nativeQuery = true)
    OpCard findByCardId(Integer id);

    @Modifying
    @Query(value = "delete from op_user_card where card_id = ?1", nativeQuery = true)
    void removeUserCard(Integer cardId);

}
