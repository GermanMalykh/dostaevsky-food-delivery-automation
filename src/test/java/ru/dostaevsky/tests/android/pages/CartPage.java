package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;
import static java.time.Duration.*;
import static ru.dostaevsky.data.CartData.*;

public class CartPage {

    private final static SelenideElement
            emptyCartInfo = $(id("ru.dostaevsky.android:id/textEmptyDescription")),
            emptyCartImage = $(id("ru.dostaevsky.android:id/imageEmpty")),
            minimalPriceErrorLayout = $(id("ru.dostaevsky.android:id/min_price_error_layout")),
            minimalPriceErrorTitle = $(id("ru.dostaevsky.android:id/min_price_error_title")),
            productTitle = $(id("ru.dostaevsky.android:id/textTitle")),
            productPrice = $(id("ru.dostaevsky.android:id/textProductPrice")),
            productCount = $(id("ru.dostaevsky.android:id/textProductCount"));

    @Step("Проверяем наличие сообщения с предложением добавления товара в корзину")
    public CartPage checkCartEmptyInfo() {
        emptyCartInfo.shouldHave(text(EMPTY_CART_INFO_TEXT));
        return this;
    }

    @Step("Проверяем наличие изображения пустой корзины")
    public CartPage checkCartEmptyImage() {
        emptyCartImage.shouldBe(visible);
        return this;
    }

    @Step("Проверяем наличие сообщения с текстом минимальной суммы заказа")
    public CartPage checkMinimalPriceTitle(String minimalPrice) {
        minimalPriceErrorLayout
                .$(id("ru.dostaevsky.android:id/min_price_error_title"))
                .shouldBe(visible, ofSeconds(10))
                .shouldHave(text("Минимальная сумма заказа — " + minimalPrice + " ₽."));
        return this;
    }

    @Step("Проверяем отображение наименования товара \"{itemName}\" в корзине")
    public CartPage checkItemNameInTheCart(String itemName) {
        productTitle.shouldHave(text(itemName));
        return this;
    }

    @Step("Проверяем отображение цены товара в корзине")
    public CartPage checkItemPriceInTheCart(int parsedPrice, int count) {
        productPrice.shouldHave(text(String.valueOf(parsedPrice * count)));
        return this;
    }

    @Step("Проверяем отображение количества товара \"{itemCount}\" в корзине")
    public CartPage checkItemCountInTheCart(String itemCount) {
        productCount.shouldHave(text(itemCount));
        return this;
    }

}
