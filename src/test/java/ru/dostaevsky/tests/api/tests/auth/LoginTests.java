package ru.dostaevsky.tests.api.tests.auth;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.TestBaseApi;
import ru.dostaevsky.tests.api.models.ErrorResponse;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("Тесты на авторизацию")
public class LoginTests extends TestBaseApi {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();

    @DisplayName("Авторизация незарегистрированного в системе пользователя")
    @Test
    void unregisteredUserLogin() {
        step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem("7 000 000-00-00", "12345");
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getError()).isEqualTo("user_not_found");
            assertThat(error.getMessage()).isEqualTo("Не удалось идентифицировать пользователя.");
        });
    }

    @DisplayName("Авторизация в системе с передачей некорректного номера телефона")
    @Test
    void loginWithIncorrectPhoneNumber() {
        step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem("+79000000000", "12345");
        });
        step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("Некорректный номер телефона.");
            assertThat(error.getErrors().toString()).contains("Некорректный номер телефона.");
        });
    }

    @DisplayName("Авторизация в системе без передачи смс кода")
    @Test
    void loginWithoutSmsCodeField() {
        step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem("7 000 000-00-00", "");
        });
        step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("Поле смс кода подтверждения является обязательным.");
            assertThat(error.getErrors().toString()).contains("Поле смс кода подтверждения является обязательным.");
        });
    }
}