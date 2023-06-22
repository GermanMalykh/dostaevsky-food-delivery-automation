package ru.dostaevsky.tests.api.tests.reviews;

import io.qameta.allure.Severity;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.ApiConfig;
import ru.dostaevsky.tests.api.models.ErrorResponse;
import ru.dostaevsky.tests.api.models.ReviewsResponse;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.MINOR;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.data.AuthData.AUTH_TOKEN;
import static ru.dostaevsky.tests.api.constants.ResponseData.*;

@Tag("api")
@DisplayName("REST API")
public class GettingReviewsTests extends ApiConfig {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();

    @Severity(NORMAL)
    @DisplayName("Получение отзывов о компании с передачей авторизационного токена")
    @Test
    void gettingReviewsWithToken() {
        step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(AUTH_TOKEN, 10);
        });
        step("Ответ содержит статус код: \"200\"", () -> {
            response.statusCode(200);
        });
        step("Ответ содержит запрашиваемое количество отзывов", () -> {
            ReviewsResponse reviewsResponse = response.extract().as(ReviewsResponse.class);
            assertThat(reviewsResponse.getReviews().length).isEqualTo(10);
        });
    }

    @Severity(MINOR)
    @DisplayName("Получение отзывов о компании без передачи авторизационного токена")
    @Test
    void gettingReviewsWithoutToken() {
        step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(null, 10);
        });
        step("Ответ содержит статус код: \"401\"", () -> {
            response.statusCode(401);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(NOT_EXIST_TOKEN_MESSAGE);
        });
    }

    @Severity(MINOR)
    @DisplayName("Получение отзывов о компании с превышением лимита по отзывам в запросе")
    @Test
    void gettingReviewsWithExceedingLimit() {
        step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(AUTH_TOKEN, 120);
        });
        step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(INVALID_REQUEST_ERROR);
            assertThat(error.getErrors().toString()).contains(EXCEEDING_LIMIT_MESSAGE);
        });
    }

    @Severity(MINOR)
    @DisplayName("Получение отзывов о компании без передачи значения количества возвращаемых отзывов")
    @Test
    void gettingReviewsWithoutLimitValue() {
        step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(AUTH_TOKEN, null);
        });
        step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(INVALID_REQUEST_ERROR);
            assertThat(error.getErrors().toString()).contains(INVALID_LIMIT_FIELD_TYPE_MESSAGE);
        });
    }
}
