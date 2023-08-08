package ru.dostaevsky.tests.api.tests.basket;

import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.data.AuthData;
import ru.dostaevsky.enums.BurgerId;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.ApiConfig;
import ru.dostaevsky.tests.api.constants.ResponseData;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;

@Tag("api")
@DisplayName("Rest API Tests")
public class AddItemToBasketTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("[API] Добавление товара в корзину для пользователя с куками")
    @Test
    void addingItemToBasketWithCookies() {
        Allure.step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE,
                    BurgerId.DOR_BLUE_BURGER_ID.getValue());
        });
        Allure.step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        Allure.step("Ответ содержит информацию о добавленном товаре", () -> {
            Assertions.assertThat(response.extract().jsonPath().prettify())
                    .contains(ResponseData.SUCCESS_RESPONSE_BODY_WITH_ITEM);
        });
        Allure.step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterAddToBasket(data);
            response = apiClient.removeItemFromBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE,
                    BurgerId.DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Добавление товара в корзину для пользователя без кук")
    @Test
    void addingItemToBasketWithoutCookies() {
        Allure.step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(null, BurgerId.DOR_BLUE_BURGER_ID.getValue());
        });
        Allure.step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        Allure.step("Ответ содержит информацию об ошибке добавления товара в корзину", () -> {
            Assertions.assertThat(response.extract().jsonPath().prettify()).contains(ResponseData.ERROR_RESPONSE_BODY);
        });
    }
}
