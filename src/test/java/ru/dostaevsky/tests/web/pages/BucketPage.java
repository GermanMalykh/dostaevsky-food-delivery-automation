package ru.dostaevsky.tests.web.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class BucketPage {
    @Step("Проверяем наличие сообщения с предложением добавления позиций в корзину")
    public BucketPage checkBucketEmptyInfo() {
        $(".basket-empty-blank").shouldHave(text("Добавьте что-нибудь в корзину"));
        return this;
    }

    @Step("Проверяем наличие изображения пустой корзины")
    public BucketPage checkBucketEmptyImage() {
        $(".basket-empty-blank__img").shouldHave(attributeMatching("src", ".*/files/images/errors/emptycart.svg"));
        return this;
    }

    @Step("Проверяем наличие сообщения с текстом минимальной суммы заказа")
    public BucketPage checkBucketLimitTitle() {
        $(".basket-blank-limit__title").shouldBe(visible).shouldHave(text("Минимальная сумма заказа 500 ₽"));
        return this;
    }

    @Step("Добавляем позицию в корзину и проверяем, что цена количества и наименования товара в корзине соответствует добавленной")
    public BucketPage checkingThePriceOfTheItemAddedToTheCart(String itemPrice) {
        $(".basket__product-price__value")
                .shouldBe(visible)
                .shouldHave(text(itemPrice));
        return this;
    }

    @Step("Добавляем позицию в корзину и проверяем, что цена количества и наименования товара в корзине соответствует добавленной")
    public BucketPage checkingTheQuantityOfTheItemAddedToTheCart(String itemCount) {
        $(".counter-buttons__count")
                .shouldBe(visible)
                .shouldHave(text(itemCount));
        return this;
    }

    @Step("Добавляем позицию в корзину и проверяем, что цена количества и наименования товара в корзине соответствует добавленной")
    public BucketPage checkingTheNameOfTheProductAddedToTheCart(String itemName) {
        $(".basket__product-title")
                .shouldBe(visible)
                .shouldHave(text(itemName));
        return this;
    }

}
