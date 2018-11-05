package com.example.demo.controller;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.with;

import com.example.demo.entity.Card;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CardControllerTest {

    @Test
    public void basicGetTest() {
        given().when().get("/cards").then().statusCode(200);
    }


    @Test
    public void invalidGetCard() {
        given().when().get("/cards/999")
                .then().statusCode(400);
    }

    @Test
    public void getCard() throws Exception {
        given().when().get("/cards/999")
                .then().statusCode(400);
    }

    @Test
    public void getCardByType() throws Exception {
        Card card = new Card();
        card.setCardHolderName("xyx1111");
        card.setCardNumber(444433333444L);
        with().body(card).accept(ContentType.JSON).contentType(ContentType.JSON).when().post("/cards").then().statusCode(200);


        card = new Card();
        card.setCardHolderName("xyx1111");
        card.setCardNumber(5121766222L);
        with().body(card).accept(ContentType.JSON).contentType(ContentType.JSON).when().post("/cards").then().statusCode(200);


        Response response = given().queryParam("cardType","MC").queryParam("cardSubType", "GOLD_CREDIT").
                when().
                get("/cards");

        com.jayway.restassured.path.json.JsonPath jsonPathEvaluator = response.jsonPath();
        ArrayList cardId = jsonPathEvaluator.get("cardId");
        Assert.assertEquals(1,cardId.size());


    }

    @Test
    public void saveCard() throws Exception {

        Card card = new Card();
        card.setCardHolderName("xyx1111");
        card.setCardNumber(667876969699878L);
        with().body(card).accept(ContentType.JSON).contentType(ContentType.JSON).when().post("/cards").then().statusCode(200);
    }

    @Test
    public void updateCard() throws Exception {
        Card card = new Card();
        card.setCardHolderName("xyx1112");
        card.setCardNumber(667876969697879L);
        Response response =with().body(card).accept(ContentType.JSON).contentType(ContentType.JSON).post("/cards");
        String responseString = response.getBody().asString();
        Assert.assertTrue(responseString.contains("cardId"));

        com.jayway.restassured.path.json.JsonPath jsonPathEvaluator = response.jsonPath();
        ArrayList cardId = jsonPathEvaluator.get("cardId");

        card = new Card();
        card.setCardHolderName("xyx1111");
        card.setCardNumber(4444333334445L);

        with().body(card).accept(ContentType.JSON).contentType(ContentType.JSON).when().put("/cards/"+cardId.get(cardId.size()-1)).then().statusCode(200);
    }

    @Test
    public void deleteCard() throws Exception {


        given().when().delete("/cards/999")
                .then().statusCode(400);

        Card card = new Card();
        card.setCardHolderName("xyx1112");
        card.setCardNumber(667876969697879L);
        Response response =with().body(card).accept(ContentType.JSON).contentType(ContentType.JSON).post("/cards");

        com.jayway.restassured.path.json.JsonPath jsonPathEvaluator = response.jsonPath();
        ArrayList cardId = jsonPathEvaluator.get("cardId");



        given().when().delete("/cards/"+cardId.get(cardId.size()-1)).then().statusCode(200);
    }

}