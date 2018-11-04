package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Card implements Serializable{

    private static final long serialVersionUID = 0x45A6DA99BBBDA8A8L;
	
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long cardId;
    @Column
    private Long cardNumber;
    @Column
    private CardType cardType;
    @Column
    private CardSubType cardSubType;
    @Column
    private String cardHolderName;
    @Column
    private String nickName;

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardSubType getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(CardSubType cardSubType) {
        this.cardSubType = cardSubType;
    }

    public Card() {
    }

    public Card(Long cardNumber) {
        this.cardNumber = cardNumber;
    }
}
