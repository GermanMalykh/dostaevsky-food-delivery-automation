package ru.dostaevsky.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;

@DisplayName("Web Tests")
public class DostaevskyTest extends TestBase {

    @CsvFileSource(resources = "/csv/city.csv")
    @ParameterizedTest(name = "Взаимодействие с городом {1} из списка доступных для выбора")
    @DisplayName("UI тест: ")
    void selectingCityFromTheListAvailableAndCheckingDisplayTest(String link, String city) {
        step("Переходим на главную страницу", () -> {
            open("https://dostaevsky.ru/");
            executeJavaScript("arguments[0].setAttribute('hidden', 'true')", $(".city-confirm"));
        });
        step("Выбираем в списке город", () -> {
            $(".main-nav__city").hover()
                    .$("a[href*='" + link + "']")
                    .shouldHave(Condition.text(city)).click();
        });
        step("Проверяем, что для выбранного города отображается его ссылка", () -> {
            webdriver().shouldHave(url(link));
        });
        step("Проверяем, что наименование выбранного города отображается на странице", () -> {
            executeJavaScript("arguments[0].setAttribute('hidden', 'true')", $(".city-confirm"));
            $("a[href*='" + link + "']").shouldHave(Condition.attribute("data-city", "selected"));
        });
    }
}
