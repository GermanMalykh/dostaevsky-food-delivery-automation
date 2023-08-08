package ru.dostaevsky.tests.api.tests.reviews;

import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.data.AuthData;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.ApiConfig;
import ru.dostaevsky.tests.api.constants.ResponseData;
import ru.dostaevsky.tests.api.models.ErrorResponse;
import ru.dostaevsky.tests.api.models.ReviewsResponse;

@Tag("api")
@DisplayName("Rest API Tests")
public class GettingReviewsTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("[API] Получение отзывов о компании с передачей авторизационного токена")
    @Test
    void gettingReviewsWithToken() {
        Allure.step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(AuthData.AUTH_TOKEN, 10);
        });
        Allure.step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        Allure.step("Ответ содержит запрашиваемое количество отзывов", () -> {
            ReviewsResponse reviewsResponse = response.extract().as(ReviewsResponse.class);
            Assertions.assertThat(reviewsResponse.getReviews().length).isEqualTo(10);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Получение отзывов о компании без передачи авторизационного токена")
    @Test
    void gettingReviewsWithoutToken() {
        Allure.step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(null, 10);
        });
        Allure.step("Ответ содержит статус код: \"401\"", () -> {
            response.statusCode(401);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.NOT_EXIST_TOKEN_MESSAGE);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Получение отзывов о компании с превышением лимита по отзывам в запросе")
    @Test
    void gettingReviewsWithExceedingLimit() {
        Allure.step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(AuthData.AUTH_TOKEN, 120);
        });
        Allure.step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.INVALID_REQUEST_ERROR);
            Assertions.assertThat(error.getErrors().toString()).contains(ResponseData.EXCEEDING_LIMIT_MESSAGE);
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[API] Получение отзывов о компании без передачи значения количества возвращаемых отзывов")
    @Test
    void gettingReviewsWithoutLimitValue() {
        Allure.step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(AuthData.AUTH_TOKEN, null);
        });
        Allure.step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        Allure.step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            Assertions.assertThat(error.getMessage()).isEqualTo(ResponseData.INVALID_REQUEST_ERROR);
            Assertions.assertThat(error.getErrors().toString()).contains(ResponseData.INVALID_LIMIT_FIELD_TYPE_MESSAGE);
        });
    }
}
