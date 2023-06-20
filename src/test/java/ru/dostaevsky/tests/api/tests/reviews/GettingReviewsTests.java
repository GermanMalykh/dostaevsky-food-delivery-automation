package ru.dostaevsky.tests.api.tests.reviews;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.config.TestBaseApi;
import ru.dostaevsky.tests.api.models.ErrorResponse;
import ru.dostaevsky.tests.api.models.ReviewsResponse;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.dostaevsky.data.AuthData.AUTH_TOKEN;

@Tag("api")
@DisplayName("Тесты на получение отзывов о компании")
public class GettingReviewsTests extends TestBaseApi {
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();

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
            assertThat(error.getMessage()).isEqualTo("Токен не существует");
        });
    }

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
            assertThat(error.getMessage()).isEqualTo("Некорректный запрос, попробуйте изменить отправляемые данные.");
            assertThat(error.getErrors().toString()).contains("The limit may not be greater than 100.");
        });
    }

    @DisplayName("Получение отзывов о компании без передачи значения количества возвращаемых отзывов")
    @Test
    void gettingReviewsWithExceedingLimit1() {
        step("Делаем запрос на получение отзывов о компании", () -> {
            response = apiClient.gettingReviews(AUTH_TOKEN, null);
        });
        step("Ответ содержит статус код: \"400\"", () -> {
            response.statusCode(400);
        });
        step("Ответ содержит сообщение об ошибке", () -> {
            ErrorResponse error = response.extract().as(ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("Некорректный запрос, попробуйте изменить отправляемые данные.");
            assertThat(error.getErrors().toString()).contains("The limit must be an integer.");
        });
    }
}
