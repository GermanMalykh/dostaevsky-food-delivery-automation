package ru.dostaevsky.tests.api;

import io.restassured.response.ValidatableResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.TestBaseApi;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;
import ru.dostaevsky.tests.api.models.BasketInfoResponse;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.data.CookieData.UNREGISTERED_USER_COOKIE;
import static ru.dostaevsky.enums.BurgerIds.DOR_BLUE_BURGER_ID;
import static ru.dostaevsky.tests.api.constants.ResponseData.*;

@Tag("api")
@DisplayName("REST API Tests")
public class BasketApiTests extends TestBaseApi {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();

    @DisplayName("Получение информации о заполненной корзине")
    @Test
    void gettingFilledBasketInfo() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(
                            UNREGISTERED_USER_COOKIE,
                            DOR_BLUE_BURGER_ID.getValue())
                    .statusCode(200);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(UNREGISTERED_USER_COOKIE);
        });
        step("Проверяем, что статус код равен 200", () -> {
            response.statusCode(200);
        });
        step("Проверяем ответ (потом придумаю текст)", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            assertThat(basketInfoResponse.getPrice()).isGreaterThan(0);
            assertThat(basketInfoResponse.getMinCartPrice()).isEqualTo(500);
        });
        step("Очищаем корзину", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(
                            UNREGISTERED_USER_COOKIE,
                            DOR_BLUE_BURGER_ID.getValue(),
                            itemUid)
                    .statusCode(200);
        });
    }

    @DisplayName("Получение информации о пустой корзине")
    @Test
    void gettingEmptyBasketInfo() {
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(null);
        });
        step("Проверяем, что статус код равен 200", () -> {
            response.statusCode(200);
        });
        step("Проверяем ответ (потом придумаю текст)", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            assertThat(basketInfoResponse.getPrice()).isEqualTo(0);
            assertThat(basketInfoResponse.getMinCartPrice()).isEqualTo(500);
        });
    }

    @DisplayName("Добавление товара в корзину для пользователя с куками")
    @Test
    void addingItemToBasketWithCookies() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(
                    UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue());
        });
        step("Проверяем, что статус код равен 200", () -> {
            response.statusCode(200);
        });
        step("Проверяем ответ (потом придумаю текст)", () -> {
            assertThat(response.extract().jsonPath().prettify())
                    .contains(SUCCESS_RESPONSE_WITH_ITEM);
        });
        step("Очищаем корзину", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterAddToBasket(data);
            response = apiClient.removeItemFromBasket(
                    UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue(),
                    itemUid);
        });
    }

    @DisplayName("Добавление товара в корзину для пользователя без кук")
    @Test
    void addingItemToBasketWithoutCookies() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(
                    null,
                    DOR_BLUE_BURGER_ID.getValue());
        });
        step("Проверяем, что статус код равен 200", () -> {
            response.statusCode(200);
        });
        step("Проверяем ответ (потом придумаю текст)", () -> {
            assertThat(response.extract().jsonPath().prettify())
                    .contains(ERROR_RESPONSE);
        });
    }

    @DisplayName("Удаление товара из корзины")
    @Test
    void removedItemFromBasket() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(
                            UNREGISTERED_USER_COOKIE,
                            DOR_BLUE_BURGER_ID.getValue())
                    .statusCode(200);
        });
        step("Очищаем корзину", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterAddToBasket(data);
            response = apiClient.removeItemFromBasket(
                    UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue(),
                    itemUid);
        });
        step("Проверяем, что статус код равен 200", () -> {
            response.statusCode(200);
        });
        step("Проверяем ответ (потом придумаю текст)", () -> {
            assertThat(response.extract().jsonPath().prettify())
                    .contains(SUCCESS_RESPONSE_WITHOUT_ITEM);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(UNREGISTERED_USER_COOKIE).statusCode(200);
        });
        step("Проверяем ответ (потом придумаю текст)", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            assertThat(basketInfoResponse.getPrice()).isEqualTo(0);
            assertThat(basketInfoResponse.getMinCartPrice()).isEqualTo(500);
        });
    }

}
