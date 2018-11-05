package com.example.demo.controller;

import com.google.common.base.Enums;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Card;
import com.example.demo.entity.CardSubType;
import com.example.demo.entity.CardType;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.utils.Constants;

@RestController
public class CardController {
	@Autowired
    CardRepository cardRepository;

	@GetMapping("/cards/{cardId}")
    public Card getCard(@PathVariable Long cardId) {

        Optional<Card> card = cardRepository.findById(cardId);

        if (!card.isPresent()) {
            throw new CardException("id-" + cardId);
        }

	    return card.get();
    }

    @RequestMapping(value = "/cards", method=RequestMethod.GET)
    public List<Card> getCardBy(
            @RequestParam("cardType") Optional<String> cardType,
            @RequestParam("cardSubType") Optional<String> cardSubType ) {

       CardType cardTypeObj = null;
       CardSubType cardSubTypeObj = null;
       List<Card> value;
       if(!cardType.isPresent() && !cardSubType.isPresent()) {
           return cardRepository.findAll();
       }
       if (cardType.isPresent()) {
           cardTypeObj = Enums.getIfPresent(CardType.class,cardType.get()).orNull();

           if (cardTypeObj == null) {
               throw new CardException("Card Type-" + cardType);
           }

       } if (cardSubType.isPresent()) {
           cardSubTypeObj = Enums.getIfPresent(CardSubType.class, cardSubType.get()).orNull();

           if (cardSubTypeObj == null) {
               throw new CardException("Card Sub Type-" + cardSubType);
           }
       }

       if (cardTypeObj != null && cardSubTypeObj != null) {
           value = cardRepository.findByCardTypeAndCardSubType(cardTypeObj, cardSubTypeObj);
       } else if ( cardTypeObj == null) {
           value = cardRepository.findByCardSubType(cardSubTypeObj);
       } else {
           value = cardRepository.findByCardType(cardTypeObj);
       }
	    return value;

    }

	@PostMapping(path="/cards", consumes = "application/json", produces = "application/json")
	public List<Card> saveCard(@RequestBody Card card) {

	    if (card.getCardNumber() == null || card.getCardHolderName() == null) {
            throw new CardException(" invalid parameter");
        } if (cardRepository.findByCardNumber(card.getCardNumber()).isPresent()) {
	        throw new CardException("Card already Present");
        }
	    setCardTypeAndSubtype(card);
		cardRepository.save(card);
		return cardRepository.findAll();
	}

    private void setCardTypeAndSubtype(Card card) {

        long nineDigits = get9Digits(card.getCardNumber());

        if (nineDigits >= Constants.MC_BUSINESS_BEGIN  && nineDigits <= Constants.MC_BUSINESS_END) {
            card.setCardType(CardType.MC);
            card.setCardSubType(CardSubType.BUSINESS);
        } else if (nineDigits >= Constants.VISA_PREMIUM_CREDIT_BEGIN  && nineDigits <= Constants.VISA_PREMIUM_CREDITEND) {
            card.setCardType(CardType.VISA);
            card.setCardSubType(CardSubType.PREMIUM_CREDIT);
        } else if (nineDigits >= Constants.MC_GOLD_CREDIT_BEGIN && nineDigits <= Constants.MC_GOLD_CREDIT_END) {
            card.setCardType(CardType.MC);
            card.setCardSubType(CardSubType.GOLD_CREDIT);
        } else if ((card.getCardNumber()+"").startsWith(Constants.VISA_CREDIT)) {
            card.setCardType(CardType.VISA);
            card.setCardSubType(CardSubType.CREDIT);
        } else if ((card.getCardNumber()+"").startsWith(Constants.VISA_DEBIT)) {
            card.setCardType(CardType.VISA);
            card.setCardSubType(CardSubType.DEBIT);
        } else if ((card.getCardNumber()+"").startsWith(Constants.AMEX_CREDIT)) {
            card.setCardType(CardType.AMEX);
            card.setCardSubType(CardSubType.CREDIT);
        } else if ((card.getCardNumber()+"").startsWith(Constants.MC_CREDIT)) {
            card.setCardType(CardType.MC);
            card.setCardSubType(CardSubType.CREDIT);
        } else {
            card.setCardType(CardType.UNKNOWN);
            card.setCardSubType(CardSubType.UNKNOWN);
        }
	}

    private long get9Digits(Long n) {
        while (n > 999999999) {
            n = n / 10;
        }
        return n;
    }

    @PutMapping("/cards/{cardId}")
    public List<Card> updateCard(@PathVariable Long cardId, @RequestBody Card card) {

        if (card.getCardNumber() == null || card.getCardHolderName() == null) {
            throw new CardException(" invalid parameter");
        } if (cardRepository.findByCardNumber(card.getCardNumber()).isPresent()) {
            throw new CardException("Card already Present");
        }
        setCardTypeAndSubtype(card);
        card.setCardId(cardId);
        cardRepository.save(card);
        return cardRepository.findAll();
    }

    @DeleteMapping("/cards/{cardId}")
    public List<Card> deleteCard(@PathVariable Long cardId) {

        Optional<Card> card = cardRepository.findById(cardId);

        if (!card.isPresent()) {
            throw new CardException("id-" + cardId);
        }
        cardRepository.deleteById(cardId);
        return cardRepository.findAll();
    }
}
