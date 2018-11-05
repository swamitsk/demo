package com.example.demo.repository;

import com.example.demo.entity.Card;
import com.example.demo.entity.CardSubType;
import com.example.demo.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
    List<Card> findByCardTypeAndCardSubType(CardType cardType, CardSubType cardSubType);
    List<Card> findByCardSubType( CardSubType cardSubType);
    List<Card> findByCardType(CardType cardType);
    Optional<Card> findByCardNumber(Long cardNumber);


}
