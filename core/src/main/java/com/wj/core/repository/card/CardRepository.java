package com.wj.core.repository.card;


import com.wj.core.entity.card.OpCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<OpCard, Integer> {
}
