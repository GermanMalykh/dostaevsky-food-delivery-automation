package ru.dostaevsky.tests.android;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.appium.AppiumSelectors.byText;
import static com.codeborne.selenide.appium.SelenideAppium.back;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;
import static org.openqa.selenium.By.className;

public class LocalAndroidDemoTest extends TestBaseMobile {

    @Severity(CRITICAL)
    @CsvSource({"Санкт-Петербург, пицца", "Москва, пицца", "Сочи, пицца", "Краснодар, пицца", "Новосибирск, пицца"})
    @ParameterizedTest(name = "Для города {0} результаты поиска по слову \"{1}\" не равны нулю")
    void successfulProductSearchTest(String city, String product) {
        step("Выбираем город", () -> {
            $(byText(city)).shouldBe(visible, Duration.ofSeconds(90)).click();
            back();
        });
        step("Переходим к поиску", () -> {
            $(id("ru.dostaevsky.android:id/search_button")).click();
        });
        step("Указываем параметры поиска", () -> {
            $(id("ru.dostaevsky.android:id/search_src_text")).sendKeys(product);
        });
        step("Проверяем, что результаты поиска не равны нулю", () -> {
            $$(id("ru.dostaevsky.android:id/rootProductView"))
                    .shouldHave(sizeGreaterThan(0));
        });
    }

    @Severity(MINOR)
    @DisplayName("Отображение информации в пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        step("Выбираем город", () -> {
            $(byText("Санкт-Петербург")).shouldBe(visible, Duration.ofSeconds(90)).click();
            back();
        });
        step("Переходим корзину", () -> {
            $(byText("Корзина")).click();
        });
        step("Проверяем наличие сообщения с предложением добавления позиций в корзину", () -> {
            $(id("ru.dostaevsky.android:id/textEmptyDescription"))
                    .shouldHave(text("Добавьте что-нибудь в корзину"));
        });
        step("Проверяем наличие изображения пустой корзины", () -> {
            $(id("ru.dostaevsky.android:id/imageEmpty"))
                    .shouldBe(visible);
        });
    }

    @Severity(NORMAL)
    @DisplayName("Отображение информации о минимальной сумме заказа")
    @Test
    void addItemToCartAndCheckMinimalPriceToDeliveryInfo() {
        step("Выбираем город", () -> {
            $(byText("Санкт-Петербург")).shouldBe(visible, Duration.ofSeconds(90)).click();
            back();
        });
        step("Переходим к фастфуду", () -> {
            $(byText("Фастфуд")).click();
        });
        step("Добавляем одну позицию в корзину", () -> {
            $(id("ru.dostaevsky.android:id/addButton")).click();
        });
        step("Переходим в корзину", () -> {
            $(byText("Корзина")).click();
        });
        step("Проверяем наличие сообщения с текстом минимальной суммы заказа", () -> {
            $(id("ru.dostaevsky.android:id/min_price_error_layout"))
                    .$(id("ru.dostaevsky.android:id/min_price_error_title"))
                    .shouldHave(text("Минимальная сумма заказа — 500 ₽"));
        });
    }

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара")
    @Test
    void addItemToCartAndCheckValueTest() {
        step("Выбираем город", () -> {
            $(byText("Санкт-Петербург")).shouldBe(visible, Duration.ofSeconds(90)).click();
            back();
        });
        step("Переходим к новинкам", () -> {
            $(byText("Новинки")).click();
        });
        step("Добавляем две позиции в корзину", () -> {
            $(id("ru.dostaevsky.android:id/addButton")).click();
            $(id("ru.dostaevsky.android:id/buttonAddMoreProduct")).click();
        });
        step("Переходим в корзину и проверяем, что цена количество и наименования товара в корзине соответствует добавленной", () -> {

            int parsedPrice =
                    Integer.parseInt(
                            $(id("ru.dostaevsky.android:id/textCardProductPrice"))
                                    .getText().replace(" ₽", "")
                    );

            String itemName = $(id("ru.dostaevsky.android:id/textProductName")).getText();
            String itemCount = $(id("ru.dostaevsky.android:id/textViewCount")).getText();

            $(id("ru.dostaevsky.android:id/bottomNavigationCartMenuId"))
                    .$(accessibilityId("Корзина, " + itemCount + " new notifications"))
                    .shouldBe(visible);

            $(byText("Корзина")).click();

            $(id("ru.dostaevsky.android:id/textTitle")).shouldHave(text(itemName));
            $(id("ru.dostaevsky.android:id/textProductPrice")).shouldHave(text(String.valueOf(parsedPrice * 2)));
            $(id("ru.dostaevsky.android:id/textProductCount")).shouldHave(text(itemCount));
        });
    }

    @Severity(NORMAL)
    @DisplayName("Добавление товара в избранное и проверка отображения товара в списке избранных")
    @Test
    void addItemToFavoriteListAndCheckIt() {
        step("Выбираем город", () -> {
            $(byText("Санкт-Петербург")).shouldBe(visible, Duration.ofSeconds(90)).click();
            back();
        });
        $(byText(("Онигири"))).scrollTo().click();
        $(byText(("Онигири со снежным крабом"))).scrollTo().click();
        $(id(("ru.dostaevsky.android:id/imageFavorite"))).click();
        back();
        $(byText(("Еще"))).click();
        $(byText(("Избранное"))).click();
        $(className(("android.widget.TextView")))
                .shouldHave(text("Избранное"));
        $(id(("ru.dostaevsky.android:id/textProductName")))
                .shouldHave(text("Онигири со снежным крабом"));
    }

    @Severity(CRITICAL)
    @DisplayName("Выбор и отображение информации города. ")
    @ParameterizedTest(name = "Для города \"{0}\" отображается информация с контактным номером телефона \"{1}\"")
    @CsvFileSource(resources = "/csv/cityMobileInfo.csv")
    void selectingCityFromTheListAvailableAndCheckingDisplayInfoTest(String city, String phoneWithPlus) {
        step("Выбираем город", () -> {
            $(byText(city)).shouldBe(visible, Duration.ofSeconds(90)).click();
            back();
        });
        step("Переходим в раздел дополнительной информации", () -> {
            $(byText(("Еще"))).click();
        });
        step("Проверяем, что наименование выбранного города отображается на экране", () -> {
            $(id("ru.dostaevsky.android:id/cityTextView")).shouldHave(text(city));
        });
        step("Проверяем, что для выбранного города отображается правильный контактный номер телефона", () -> {
            $(id("ru.dostaevsky.android:id/call_text")).shouldHave(text(phoneWithPlus));
        });
    }
}
