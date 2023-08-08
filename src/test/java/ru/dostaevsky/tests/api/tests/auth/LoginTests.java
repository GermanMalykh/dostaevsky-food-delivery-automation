package ru.dostaevsky.tests.api.tests.auth;

import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.ApiConfig;
import ru.dostaevsky.tests.api.constants.RequestData;
import ru.dostaevsky.tests.api.constants.ResponseData;
import ru.dostaevsky.tests.api.models.ErrorResponse;

@Tag("api")
@DisplayName("Rest API Tests")
public class LoginTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("[API] Авторизация незарегистрированного в системе пользователя")
    @Test
    void unregisteredUserLogin() {
        Allure.step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem(RequestData.VALID_PHONE_NUMBER, RequestData.SMS_CODE);
        });
        Allure.step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getError()).isEqualTo(ResponseData.USER_NOT_FOUND_ERROR);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.FAILED_AUTHENTICATE_USER_MESSAGE);
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("[API] Авторизация в системе с передачей некорректного номера телефона")
    @Test
    void loginWithIncorrectPhoneNumber() {
        Allure.step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem(RequestData.INVALID_PHONE_NUMBER, RequestData.SMS_CODE);
        });
        Allure.step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.INVALID_PHONE_NUMBER_MESSAGE);
            Assertions.assertThat(error.getErrors().toString()).contains(ResponseData.INVALID_PHONE_NUMBER_MESSAGE);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Авторизация в системе без передачи смс кода")
    @Test
    void loginWithoutSmsCodeField() {
        Allure.step("Делаем запрос на авторизацию в системе", () -> {
            response = apiClient.loginInSystem(RequestData.VALID_PHONE_NUMBER, RequestData.EMPTY_VALUE);
        });
        Allure.step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.REQUIRED_SMS_FIELD_MESSAGE);
            Assertions.assertThat(error.getErrors().toString()).contains(ResponseData.REQUIRED_SMS_FIELD_MESSAGE);
        });
    }
}
