package ru.dostaevsky.tests.web.pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class HeaderComponents {
    private final static SelenideElement
            price = $(".catalog-cart__price-count"),
            count = $(".catalog-cart__count"),
            cartIcon = $(".catalog-cart__icon");

    @Step("Проверяем, что цена товара в корзине соответствует добавленной")
    public HeaderComponents checkingTheTotalAmountOfTheItemInTheHeaderCart(String itemPrice) {
        price.shouldHave(text(itemPrice));
        return this;
    }

    @Step("Проверяем, что количество товара в корзине соответствует добавленной")
    public HeaderComponents checkingTheTotalNumberOfItemsInTheHeaderCart(String itemCount) {
        count.shouldHave(text(itemCount));
        return this;
    }

    @Step("Переходим в корзину")
    public HeaderComponents navigateToBucket() {
        cartIcon.click();
        return this;
    }

}
