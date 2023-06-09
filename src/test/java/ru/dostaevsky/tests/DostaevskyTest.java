package ru.dostaevsky.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.dostaevsky.enums.CityLinks;
import ru.dostaevsky.enums.CityName;

import java.util.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Web Tests")
public class DostaevskyTest extends TestBase {

    @DisplayName("Выбор и отображение информации города. ")
    @ParameterizedTest(name = "Для города {1} отображается информация по ссылке {0} с контактным номером телефона {2}")
    @CsvFileSource(resources = "/csv/city.csv")
    void selectingCityFromTheListAvailableAndCheckingDisplayInfoTest(String link, String city, String phone) {
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
        step("Проверяем, что для выбранного города отображается правильный номер для связи", () -> {
            $(".header__phone").shouldHave(Condition.text(phone));
        });
    }

    @DisplayName("Отображение цен на завтрак. ")
    @ParameterizedTest(name = "В городе {0} отображаемые на странице цены на завтрак равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.BreakfastPriceProvider#provide")
    void breakfastPriceComparisonInSelectedCityTest(CityName name, CityLinks link, Map<String, Integer> expectedPrices) {
        step("Переходим на главную страницу", () -> {
            open("https://dostaevsky.ru/");
            executeJavaScript("arguments[0].setAttribute('hidden', 'true')", $(".city-confirm"));
        });
        step("Выбираем город", () -> {
            $(".main-nav__city").hover()
                    .$("a[href*='" + link.getValue() + "']").click();
        });
        step("Переходим к завтракам", () -> {
            $$(".main-nav__link").findBy(Condition.text("Завтраки")).click();
        });
        step("Проверяем наличие всех позиций завтрака и соответствие цен в выбранном городе", () -> {
            executeJavaScript("$('.info').remove()");

            List<SelenideElement> items = $$(".catalog-list__item");

            assertThat(items).hasSize(expectedPrices.size());

            Map<String, Integer> actualPrices = new HashMap<>();
            for (SelenideElement item : items) {
                String itemName = item.getAttribute("data-name");
                Integer itemPrice = Integer.valueOf(Objects.requireNonNull(item.getAttribute("data-price")));
                actualPrices.put(itemName, itemPrice);
            }

            assertThat(expectedPrices).containsAllEntriesOf(actualPrices);
        });
    }

}
