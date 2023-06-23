package ru.dostaevsky.tests.api.tests.basket;

import io.qameta.allure.Severity;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.ApiConfig;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;
import ru.dostaevsky.tests.api.models.BasketInfoResponse;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.data.AuthData.API_SPB_UNREGISTERED_USER_COOKIE;
import static ru.dostaevsky.enums.BurgerIds.DOR_BLUE_BURGER_ID;

@Tag("api")
@DisplayName("Rest API Tests")
public class BasketInfoTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();

    @Severity(BLOCKER)
    @DisplayName("Получение информации о заполненной корзине")
    @Test
    void gettingFilledBasketInfo() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue()).statusCode(200);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(API_SPB_UNREGISTERED_USER_COOKIE);
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Ответ содержит информацию о заполненной корзине", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            assertThat(basketInfoResponse.getPrice()).isGreaterThan(0);
            assertThat(basketInfoResponse.getItems()).isNotEmpty();
        });
        step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
    }

    @Severity(BLOCKER)
    @DisplayName("Получение информации о пустой корзине")
    @Test
    void gettingEmptyBasketInfo() {
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(API_SPB_UNREGISTERED_USER_COOKIE);
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Ответ содержит информацию о пустой корзине", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            assertThat(basketInfoResponse.getPrice()).isEqualTo(0);
            assertThat(basketInfoResponse.getItems()).isEmpty();
        });
    }
}
