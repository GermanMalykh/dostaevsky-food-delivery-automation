package ru.dostaevsky.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.dostaevsky.enums.CityLinks;
import ru.dostaevsky.enums.CityName;

import java.util.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Web Tests")
public class DostaevskyTest extends TestBase {

    @Severity(CRITICAL)
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

    @Severity(CRITICAL)
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

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара")
    @Test
    void addItemToCartAndCheckValueTest() {
        step("Переходим на главную страницу", () -> {
            open("https://dostaevsky.ru/");
        });
        step("Переходим к боулам", () -> {
            $$(".main-nav__link").findBy(Condition.text("Боулы")).click();
        });
        step("Добавляем одну позицию в корзину", () -> {
            $$(".catalog-list__item").first().$("[type='button']").click();
        });
        step("Добавляем позицию в корзину и проверяем, что цена количества и наименования товара в корзине соответствует добавленной", () -> {

            SelenideElement item = $$(".catalog-list__item").first();

            String itemPrice = Objects.requireNonNull(item.getAttribute("data-price"));
            String itemName = Objects.requireNonNull(item.getAttribute("data-name"));

            $(".catalog-cart__price-count").shouldHave(Condition.text(itemPrice));
            $(".catalog-cart__count").shouldHave(Condition.text("1"));

            $(".catalog-cart__icon").click();

            $(".basket__total-price").shouldHave(Condition.text(itemPrice));
            $(".counter-buttons__count").shouldHave(Condition.text("1"));
            $(".basket__product-title").shouldHave(Condition.text(itemName));
        });
    }

    @Severity(NORMAL)
    @DisplayName("Отображение информации о минимальной сумме заказа")
    @Test
    void addItemToCartAndCheckMinimalPriceToDeliveryInfo() {
        step("Переходим на главную страницу", () -> {
            open("https://dostaevsky.ru/");
        });
        step("Переходим к вокам", () -> {
            $$(".main-nav__link").findBy(Condition.text("Wok")).click();
        });
        step("Добавляем одну позицию в корзину", () -> {
            $$(".catalog-list__item").first().$("[type='button']").click();
        });
        step("Переходим в корзину", () -> {
            $(".catalog-cart__icon").click();
        });
        step("Проверяем наличие сообщения с текстом минимальной суммы заказа", () -> {
            $(".basket-blank-limit__title").shouldHave(Condition.text("Минимальная сумма заказа 500 ₽"));
        });
    }

    @Severity(MINOR)
    @DisplayName("Отображение информации в пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        step("Переходим на главную страницу", () -> {
            open("https://dostaevsky.ru/");
        });
        step("Переходим в корзину", () -> {
            $(".catalog-cart__icon").click();
        });
        step("Проверяем наличие сообщения с предложением добавления позиций в корзину", () -> {
            $(".basket-empty-blank").shouldHave(Condition.text("Добавьте что-нибудь в корзину"));
        });
        step("Проверяем наличие изображения пустой корзины", () -> {
            $(".basket-empty-blank__img").shouldHave(Condition.attributeMatching("src", ".*/files/images/errors/emptycart.svg"));
        });
    }

}
