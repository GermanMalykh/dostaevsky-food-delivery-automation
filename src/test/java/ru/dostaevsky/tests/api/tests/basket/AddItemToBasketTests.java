package ru.dostaevsky.tests.api.tests.basket;

import io.qameta.allure.Severity;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.TestBaseApi;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.data.AuthData.API_SPB_UNREGISTERED_USER_COOKIE;
import static ru.dostaevsky.enums.BurgerIds.DOR_BLUE_BURGER_ID;
import static ru.dostaevsky.tests.api.constants.ResponseData.ERROR_RESPONSE_BODY;
import static ru.dostaevsky.tests.api.constants.ResponseData.SUCCESS_RESPONSE_BODY_WITH_ITEM;

@Tag("api")
@DisplayName("Тесты на добавление товара в корзину")
public class AddItemToBasketTests extends TestBaseApi {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();

    @Severity(BLOCKER)
    @DisplayName("Добавление товара в корзину для пользователя с куками")
    @Test
    void addingItemToBasketWithCookies() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue());
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Ответ содержит информацию о добавленном товаре", () -> {
            assertThat(response.extract().jsonPath().prettify())
                    .contains(SUCCESS_RESPONSE_BODY_WITH_ITEM);
        });
        step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterAddToBasket(data);
            response = apiClient.removeItemFromBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
    }

    @Severity(MINOR)
    @DisplayName("Добавление товара в корзину для пользователя без кук")
    @Test
    void addingItemToBasketWithoutCookies() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(null, DOR_BLUE_BURGER_ID.getValue());
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Ответ содержит информацию об ошибке добавления товара в корзину", () -> {
            assertThat(response.extract().jsonPath().prettify()).contains(ERROR_RESPONSE_BODY);
        });
    }
}
