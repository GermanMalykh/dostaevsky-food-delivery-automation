package ru.dostaevsky.tests.api.tests.basket;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.TestBaseApi;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;
import ru.dostaevsky.tests.api.models.BasketInfoResponse;
import ru.dostaevsky.tests.api.models.ErrorResponse;
import ru.dostaevsky.tests.api.models.UpdateItemCountInBasketRequest;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.data.AuthData.API_SPB_UNREGISTERED_USER_COOKIE;
import static ru.dostaevsky.enums.BurgerIds.DOR_BLUE_BURGER_ID;

@Tag("api")
@DisplayName("Тесты на обновление количества товара в корзине")
public class UpdateItemCountInTheBasketTests extends TestBaseApi {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();
    UpdateItemCountInBasketRequest updateCountInBasket = new UpdateItemCountInBasketRequest();

    @DisplayName("Обновление количества товара в корзине")
    @Test
    void updateItemCountInTheBasketWithCookies() {
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(
                            API_SPB_UNREGISTERED_USER_COOKIE,
                            DOR_BLUE_BURGER_ID.getValue())
                    .statusCode(200);
        });
        step("Указываем \"itemId\" и \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(Integer.parseInt(DOR_BLUE_BURGER_ID.getValue()));
            updateCountInBasket.setQuantity(2);
        });
        step("Делаем запрос на обновление товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(
                    API_SPB_UNREGISTERED_USER_COOKIE,
                    updateCountInBasket);
        });
        step("Проверяем, что в ответе отображается заданное количество объектов", () -> {
            BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
            assertThat(basketInfoResponse.getItems().length).isEqualTo(2);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(API_SPB_UNREGISTERED_USER_COOKIE).statusCode(200);
        });
        step("Очищаем корзину", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(
                            API_SPB_UNREGISTERED_USER_COOKIE,
                            DOR_BLUE_BURGER_ID.getValue(),
                            itemUid)
                    .statusCode(200);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(API_SPB_UNREGISTERED_USER_COOKIE).statusCode(200);
        });
        step("Очищаем корзину", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(
                            API_SPB_UNREGISTERED_USER_COOKIE,
                            DOR_BLUE_BURGER_ID.getValue(),
                            itemUid)
                    .statusCode(200);
        });
    }

    @DisplayName("Обновление количества товара в корзине без передачи идентификатора товара")
    @Test
    void updateItemCountInTheBasketWithoutItemId() {
        step("Указываем \"itemId\" и \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(null);
            updateCountInBasket.setQuantity(2);
        });
        step("Делаем запрос на обновление товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(
                    null,
                    updateCountInBasket);
        });
        step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("The given data was invalid.");
            assertThat(error.getErrors().toString()).contains("The item id field is required.");
        });
    }

    @DisplayName("Обновление количества товара в корзине без передачи значения количества товара")
    @Test
    void updateItemCountInTheBasketWithoutQuantity() {
        step("Указываем \"itemId\" и \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(Integer.parseInt(DOR_BLUE_BURGER_ID.getValue()));
            updateCountInBasket.setQuantity(null);
        });
        step("Делаем запрос на обновление товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(
                    null,
                    updateCountInBasket);
        });
        step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("The given data was invalid.");
            assertThat(error.getErrors().toString()).contains("The quantity field is required.");
        });
    }

    @DisplayName("Обновление количества товара в корзине c передачей невалидного значения идентификатора товара")
    @Test
    void updateItemCountInTheBasketWitMaxItemValue() {
        step("Указываем \"itemId\" и \"quantity\" перед выполнением запроса", () -> {
            updateCountInBasket.setItemId(0);
            updateCountInBasket.setQuantity(1);
        });
        step("Делаем запрос на обновление товара в корзине", () -> {
            response = apiClient.updateItemCountInBasket(
                    null,
                    updateCountInBasket);
        });
        step("Ответ содержит статус код: \"422\"", () -> {
            response.statusCode(422);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("The given data was invalid.");
            assertThat(error.getErrors().toString()).contains("The selected item id is invalid.");
        });
    }
}
