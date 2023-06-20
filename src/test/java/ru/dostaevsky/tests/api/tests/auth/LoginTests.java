package ru.dostaevsky.tests.api.tests.auth;

import io.qameta.allure.Severity;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.TestBaseApi;
import ru.dostaevsky.tests.api.models.ErrorResponse;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.MINOR;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.tests.api.constants.RequestData.*;
import static ru.dostaevsky.tests.api.constants.ResponseData.*;

@Tag("api")
@DisplayName("Тесты на авторизацию")
public class LoginTests extends TestBaseApi {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();

    @Severity(NORMAL)
    @DisplayName("Авторизация незарегистрированного в системе пользователя")
    @Test
    void unregisteredUserLogin() {
        step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem(VALID_PHONE_NUMBER, SMS_CODE);
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getError()).isEqualTo(USER_NOT_FOUND_ERROR);
            assertThat(error.getMessage()).isEqualTo(FAILED_AUTHENTICATE_USER_MESSAGE);
        });
    }

    @Severity(NORMAL)
    @DisplayName("Авторизация в системе с передачей некорректного номера телефона")
    @Test
    void loginWithIncorrectPhoneNumber() {
        step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem(INVALID_PHONE_NUMBER, SMS_CODE);
        });
        step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(INVALID_PHONE_NUMBER_MESSAGE);
            assertThat(error.getErrors().toString()).contains(INVALID_PHONE_NUMBER_MESSAGE);
        });
    }

    @Severity(MINOR)
    @DisplayName("Авторизация в системе без передачи смс кода")
    @Test
    void loginWithoutSmsCodeField() {
        step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem(VALID_PHONE_NUMBER, EMPTY_VALUE);
        });
        step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(REQUIRED_SMS_FIELD_MESSAGE);
            assertThat(error.getErrors().toString()).contains(REQUIRED_SMS_FIELD_MESSAGE);
        });
    }
}