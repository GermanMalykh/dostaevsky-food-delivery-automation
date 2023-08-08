package ru.dostaevsky.tests.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class HeaderComponents {
    private final SelenideElement price = Selenide.$(".catalog-cart__price-count");
    private final SelenideElement count = Selenide.$(".catalog-cart__count");
    private final SelenideElement cartIcon = Selenide.$(".catalog-cart__icon");
    private final SelenideElement phoneInfo = Selenide.$(".header__phone");

    @Step("Проверяем, что цена товара в корзине равна цене добавленного товара")
    public HeaderComponents checkItemPriceInHeaderCart(String itemPrice) {
        price.shouldHave(Condition.text(itemPrice));
        return this;
    }

    @Step("Проверяем, что количество товара в корзине равно количеству добавленного товара")
    public HeaderComponents checkItemCountInHeaderCart(String itemCount) {
        count.shouldHave(Condition.text(itemCount));
        return this;
    }

    @Step("Переходим в корзину")
    public HeaderComponents navigateToCart() {
        cartIcon.click();
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается правильный номер для связи")
    public HeaderComponents checkCityPhoneNumberOnPage(String phone) {
        phoneInfo.shouldHave(Condition.text(phone));
        return this;
    }

}
