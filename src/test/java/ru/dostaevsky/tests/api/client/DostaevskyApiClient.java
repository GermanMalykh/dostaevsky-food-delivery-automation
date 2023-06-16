package ru.dostaevsky.tests.api.client;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static ru.dostaevsky.tests.api.constants.Endpoints.*;
import static ru.dostaevsky.tests.api.specs.RestSpecs.requestSpecification;
import static ru.dostaevsky.tests.api.specs.RestSpecs.responseSpecification;

public class DostaevskyApiClient {

    @Description("GET../basket/info - Getting info about items in basket")
    public ValidatableResponse gettingBasketInfo(String cookieValue) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("Cookie", cookieValue);
        return given(requestSpecification)
                .contentType(JSON)
                .headers(headers)
                .get(BASKET_INFO)
                .then()
                .spec(responseSpecification);
    }

    @Description("GET../basket/add_to_basket?item_id=% - Adding item to basket")
    public ValidatableResponse addingItemToBasket(String cookieValue, String itemId) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", cookieValue);
        return given(requestSpecification)
                .contentType(JSON)
                .headers(headers)
                .get(String.format(ADD_ITEM_TO_BASKET, itemId))
                .then()
                .spec(responseSpecification);
    }

    @Description("GET../ajax/basket/basket_item_handler?action=removeItem&item_id=%&item_uid=% - Remove item from basket")
    public ValidatableResponse removeItemFromBasket(String cookieValue, String itemId, String itemUid) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", cookieValue);
        return given(requestSpecification)
                .contentType(JSON)
                .headers(headers)
                .get(String.format(REMOVE_ITEM_FROM_BASKET, itemId, itemUid))
                .then()
                .spec(responseSpecification);
    }
}
