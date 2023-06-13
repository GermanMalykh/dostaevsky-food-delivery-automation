package ru.dostaevsky.tests.web.pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class HeaderComponents {
    private final static SelenideElement
            price = $(".catalog-cart__price-count"),
            count = $(".catalog-cart__count"),
            cartIcon = $(".catalog-cart__icon"),
            phoneInfo = $(".header__phone");

    @Step("Проверяем, что цена товара в корзине соответствует добавленной")
    public HeaderComponents checkItemPriceInHeaderCart(String itemPrice) {
        price.shouldHave(text(itemPrice));
        return this;
    }

    //TODO: Доработать поиск элемента
    @Step("Проверяем, что количество товара в корзине соответствует добавленной")
    public HeaderComponents checkItemCountInHeaderCart(String itemCount) {
        count.shouldHave(text(itemCount));
        return this;
    }

    @Step("Переходим в корзину")
    public HeaderComponents navigateToBucket() {
        cartIcon.click();
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается правильный номер для связи")
    public HeaderComponents checkCityPhoneNumberOnPage(String phone) {
        phoneInfo.shouldHave(text(phone));
        return this;
    }

}
