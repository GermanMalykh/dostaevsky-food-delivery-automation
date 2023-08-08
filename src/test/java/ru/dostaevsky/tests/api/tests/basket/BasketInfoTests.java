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
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;
import ru.dostaevsky.tests.api.models.BasketInfoResponse;

@Tag("api")
@DisplayName("Rest API Tests")
public class BasketInfoTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("[API] Получение информации о заполненной корзине")
    @Test
    void gettingFilledBasketInfo() {
        Allure.step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE,
                    BurgerId.DOR_BLUE_BURGER_ID.getValue()).statusCode(200);
        });
        Allure.step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(AuthData.API_SPB_UNREGISTERED_USER_COOKIE);
        });
        Allure.step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        Allure.step("Ответ содержит информацию о заполненной корзине", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            Assertions.assertThat(basketInfoResponse.getPrice()).isGreaterThan(0);
            Assertions.assertThat(basketInfoResponse.getItems()).isNotEmpty();
        });
        Allure.step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE,
                    BurgerId.DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("[API] Получение информации о пустой корзине")
    @Test
    void gettingEmptyBasketInfo() {
        Allure.step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(AuthData.API_SPB_UNREGISTERED_USER_COOKIE);
        });
        Allure.step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        Allure.step("Ответ содержит информацию о пустой корзине", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            Assertions.assertThat(basketInfoResponse.getPrice()).isEqualTo(0);
            Assertions.assertThat(basketInfoResponse.getItems()).isEmpty();
        });
    }
}
