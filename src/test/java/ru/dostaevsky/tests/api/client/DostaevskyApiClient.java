package ru.dostaevsky.tests.api.client;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import ru.dostaevsky.tests.api.models.UpdateItemCountInBasketRequest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.*;
import static ru.dostaevsky.tests.api.constants.Endpoints.*;
import static ru.dostaevsky.tests.api.specs.RestSpecs.requestSpecification;
import static ru.dostaevsky.tests.api.specs.RestSpecs.responseSpecification;

public class DostaevskyApiClient {

    /**
     * Basket adapter
     */
    @Description("GET../basket/info - Getting info about items in basket")
    public ValidatableResponse gettingBasketInfo(String cookieValue) {
        Map<String, String> headers = new HashMap<>();
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
        Map<String, String> headers = new HashMap<>();
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
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookieValue);
        return given(requestSpecification)
                .contentType(JSON)
                .headers(headers)
                .get(String.format(REMOVE_ITEM_FROM_BASKET, itemId, itemUid))
                .then()
                .spec(responseSpecification);
    }

    @Description("PATCH../basket/update-item-count - Update item count in the basket")
    public ValidatableResponse updateItemCountInBasket(String cookieValue, UpdateItemCountInBasketRequest updateItemCountInBasketRequest) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookieValue);
        headers.put("X-Requested-With", "XMLHttpRequest");
        return given(requestSpecification)
                .contentType(JSON)
                .headers(headers)
                .body(updateItemCountInBasketRequest)
                .patch((UPDATE_ITEM_COUNT_IN_BASKET))
                .then()
                .spec(responseSpecification);
    }

    /**
     * Reviews adapter
     */
    @Description("GET../reviews?limit=% - Getting reviews")
    public ValidatableResponse gettingReviews(String token, Integer limitValue) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Auth-Token", token);
        Map<String, Integer> params = new HashMap<>();
        params.put("limit", limitValue);
        return given(requestSpecification)
                .contentType(JSON)
                .headers(headers)
                .queryParams(params)
                .get(GETTING_REVIEWS)
                .then()
                .spec(responseSpecification);
    }

    /**
     * Auth adapter
     */
    @Description("POST../auth/login - Login in system")
    public ValidatableResponse loginInSystem(String phoneValue, String smsCodeValue) {
        return given(requestSpecification)
                .contentType(MULTIPART)
                .multiPart("phone", phoneValue)
                .multiPart("user_sms_confirm_code", smsCodeValue)
                .post(LOGIN)
                .then()
                .spec(responseSpecification);
    }

}
