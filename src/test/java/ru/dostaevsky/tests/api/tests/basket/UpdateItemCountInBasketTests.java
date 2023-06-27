package ru.dostaevsky.tests.api.tests.basket;

import io.qameta.allure.Severity;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.ApiConfig;
import ru.dostaevsky.tests.api.helpers.JsonConverter;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;
import ru.dostaevsky.tests.api.models.BasketInfoResponse;
import ru.dostaevsky.tests.api.models.ErrorResponse;
import ru.dostaevsky.tests.api.models.UpdateItemCountInBasketRequest;

import java.io.File;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.qameta.allure.SeverityLevel.MINOR;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.data.AuthData.API_SPB_UNREGISTERED_USER_COOKIE;
import static ru.dostaevsky.enums.BurgerIds.DOR_BLUE_BURGER_ID;
import static ru.dostaevsky.tests.api.constants.FilePathConstants.UPDATE_ITEM_COUNT_IN_BASKET_JSON;
import static ru.dostaevsky.tests.api.constants.ResponseData.*;

@Tag("api")
@DisplayName("Rest API Tests")
public class UpdateItemCountInBasketTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();
    UpdateItemCountInBasketRequest updateCountInBasket = JsonConverter.deserialize(new File(UPDATE_ITEM_COUNT_IN_BASKET_JSON), UpdateItemCountInBasketRequest.class);

    @Severity(BLOCKER)
    @DisplayName("[API] Обновление количества товара в корзине")
    @Test
    void updateItemCountInTheBasket() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue()).statusCode(200);
        });
        step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(API_SPB_UNREGISTERED_USER_COOKIE, updateCountInBasket);
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Проверяем, что в ответе отображается заданное количество товара", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            assertThat(basketInfoResponse.getItems().length).isEqualTo(2);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(API_SPB_UNREGISTERED_USER_COOKIE).statusCode(200);
        });
        step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(API_SPB_UNREGISTERED_USER_COOKIE).statusCode(200);
        });
        step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
    }

    @Severity(MINOR)
    @DisplayName("[API] Обновление количества товара в корзине без передачи идентификатора товара")
    @Test
    void updateItemCountInTheBasketWithoutItemId() {
        step("Указываем \"itemId\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(null);
        });
        step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(null, updateCountInBasket);
        });
        step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(INVALID_DATA_ERROR);
            assertThat(error.getErrors().toString()).contains(REQUIRED_ITEM_ID_FIELD_MESSAGE);
        });
    }

    @Severity(MINOR)
    @DisplayName("[API] Обновление количества товара в корзине без передачи значения количества товара")
    @Test
    void updateItemCountInTheBasketWithoutQuantity() {
        step("Указываем \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setQuantity(null);
        });
        step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(null, updateCountInBasket);
        });
        step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(INVALID_DATA_ERROR);
            assertThat(error.getErrors().toString()).contains(REQUIRED_QUANTITY_FIELD_MESSAGE);
        });
    }

    @Severity(MINOR)
    @DisplayName("[API] Обновление количества товара в корзине c передачей невалидного значения идентификатора товара")
    @Test
    void updateItemCountInTheBasketWitMaxItemValue() {
        step("Указываем \"itemId\" и \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(0);
            updateCountInBasket.setQuantity(1);
        });
        step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(null, updateCountInBasket);
        });
        step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(INVALID_DATA_ERROR);
            assertThat(error.getErrors().toString()).contains(INVALID_ITEM_ID_MESSAGE);
        });
    }
}
