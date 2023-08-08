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
import ru.dostaevsky.tests.api.constants.FilePathConstants;
import ru.dostaevsky.tests.api.constants.ResponseData;
import ru.dostaevsky.tests.api.helpers.JsonConverter;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;
import ru.dostaevsky.tests.api.models.BasketInfoResponse;
import ru.dostaevsky.tests.api.models.ErrorResponse;
import ru.dostaevsky.tests.api.models.UpdateItemCountInBasketRequest;

import java.io.File;

@Tag("api")
@DisplayName("Rest API Tests")
public class UpdateItemCountInBasketTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();
    UpdateItemCountInBasketRequest updateCountInBasket = JsonConverter.deserialize(
            new File(FilePathConstants.UPDATE_ITEM_COUNT_IN_BASKET_JSON), UpdateItemCountInBasketRequest.class);

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("[API] Обновление количества товара в корзине")
    @Test
    void updateItemCountInTheBasket() {
        Allure.step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE,
                    BurgerId.DOR_BLUE_BURGER_ID.getValue()).statusCode(200);
        });
        Allure.step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE, updateCountInBasket);
        });
        Allure.step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        Allure.step("Проверяем, что в ответе отображается заданное количество товара", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            Assertions.assertThat(basketInfoResponse.getItems().length).isEqualTo(2);
        });
        Allure.step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(AuthData.API_SPB_UNREGISTERED_USER_COOKIE).statusCode(200);
        });
        Allure.step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE,
                    BurgerId.DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
        Allure.step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(AuthData.API_SPB_UNREGISTERED_USER_COOKIE).statusCode(200);
        });
        Allure.step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(AuthData.API_SPB_UNREGISTERED_USER_COOKIE,
                    BurgerId.DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Обновление количества товара в корзине без передачи идентификатора товара")
    @Test
    void updateItemCountInTheBasketWithoutItemId() {
        Allure.step("Указываем \"itemId\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(null);
        });
        Allure.step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(null, updateCountInBasket);
        });
        Allure.step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.INVALID_DATA_ERROR);
            Assertions.assertThat(error.getErrors().toString()).contains(ResponseData.REQUIRED_ITEM_ID_FIELD_MESSAGE);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Обновление количества товара в корзине без передачи значения количества товара")
    @Test
    void updateItemCountInTheBasketWithoutQuantity() {
        Allure.step("Указываем \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setQuantity(null);
        });
        Allure.step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(null, updateCountInBasket);
        });
        Allure.step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.INVALID_DATA_ERROR);
            Assertions.assertThat(error.getErrors().toString()).contains(ResponseData.REQUIRED_QUANTITY_FIELD_MESSAGE);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Обновление количества товара в корзине c передачей невалидного значения идентификатора товара")
    @Test
    void updateItemCountInTheBasketWitMaxItemValue() {
        Allure.step("Указываем \"itemId\" и \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(0);
            updateCountInBasket.setQuantity(1);
        });
        Allure.step("Делаем запрос на обновление количества товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(null, updateCountInBasket);
        });
        Allure.step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.INVALID_DATA_ERROR);
            Assertions.assertThat(error.getErrors().toString()).contains(ResponseData.INVALID_ITEM_ID_MESSAGE);
        });
    }
}
