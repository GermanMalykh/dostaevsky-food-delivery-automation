package ru.dostaevsky.tests.web.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;
import static ru.dostaevsky.data.AttributeData.*;
import static ru.dostaevsky.data.CartData.*;

public class CartPage {
    private final static SelenideElement
            emptyCartInfo = $(".basket-empty-blank"),
            emptyCartImage = $(".basket-empty-blank__img"),
            minimalPriceToDelivery = $(".basket-blank-limit__title"),
            productPrice = $(".basket__product-price__value"),
            productCount = $(".counter-buttons .counter-buttons__count"),
            productName = $(".basket__product-title");

    @Step("Проверяем наличие сообщения с предложением добавления позиций в корзину")
    public CartPage checkCartEmptyInfo() {
        emptyCartInfo
                .shouldHave(text(EMPTY_CART_INFO_TEXT));
        return this;
    }

    @Step("Проверяем наличие изображения пустой корзины")
    public CartPage checkCartEmptyImage() {
        emptyCartImage
                .shouldHave(attributeMatching(ATTRIBUTE_SRC, EMPTY_CART_IMAGE_PATH));
        return this;
    }

    @Step("Проверяем наличие сообщения с текстом минимальной суммы заказа")
    public CartPage checkMinimalPriceTitle(String minimalPrice) {
        minimalPriceToDelivery
                .shouldBe(visible, ofSeconds(10))
                .shouldHave(text("Минимальная сумма заказа " + minimalPrice + " ₽"));
        return this;
    }

    @Step("Проверяем отображение цены товара в корзине")
    public CartPage checkItemPriceInTheCart(String itemPrice) {
        productPrice
                .shouldBe(visible)
                .shouldHave(text(itemPrice));
        return this;
    }

    @Step("Проверяем отображение количества товара в корзине")
    public CartPage checkItemCountInTheCart(String itemCount) {
        productCount
                .shouldBe(visible)
                .shouldHave(text(itemCount));
        return this;
    }

    @Step("Проверяем отображение наименования товара в корзине")
    public CartPage checkItemNameInTheCart(String itemName) {
        productName
                .shouldBe(visible)
                .shouldHave(text(itemName));
        return this;
    }

}
