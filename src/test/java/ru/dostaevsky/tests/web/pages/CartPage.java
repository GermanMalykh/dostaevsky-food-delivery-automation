package ru.dostaevsky.tests.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.dostaevsky.data.AttributeData;
import ru.dostaevsky.data.CartData;

import java.time.Duration;

public class CartPage {
    private final SelenideElement emptyCartInfo = Selenide.$(".basket-empty-blank");
    private final SelenideElement emptyCartImage = Selenide.$(".basket-empty-blank__img");
    private final SelenideElement minimalPriceToDelivery = Selenide.$(".basket-blank-limit__title");
    private final SelenideElement productPrice = Selenide.$(".basket__product-price__value");
    private final SelenideElement productCount = Selenide.$(".counter-buttons .counter-buttons__count");
    private final SelenideElement productName = Selenide.$(".basket__product-title");

    @Step("Проверяем наличие сообщения с предложением добавления позиций в корзину")
    public CartPage checkCartEmptyInfo() {
        emptyCartInfo.shouldHave(Condition.text(CartData.EMPTY_CART_INFO_TEXT));
        return this;
    }

    @Step("Проверяем наличие изображения пустой корзины")
    public void checkCartEmptyImage() {
        emptyCartImage.shouldHave(Condition.attributeMatching(
                AttributeData.ATTRIBUTE_SRC, CartData.EMPTY_CART_IMAGE_PATH));
    }

    @Step("Проверяем наличие сообщения с текстом минимальной суммы заказа")
    public void checkMinimalPriceTitle(String minimalPrice) {
        minimalPriceToDelivery.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Минимальная сумма заказа " + minimalPrice + " ₽"));
    }

    @Step("Проверяем отображение цены товара в корзине")
    public CartPage checkItemPriceInTheCart(String itemPrice) {
        productPrice.shouldBe(Condition.visible).shouldHave(Condition.text(itemPrice));
        return this;
    }

    @Step("Проверяем отображение количества товара в корзине")
    public void checkItemCountInTheCart(String itemCount) {
        productCount.shouldBe(Condition.visible).shouldHave(Condition.text(itemCount));
    }

    @Step("Проверяем отображение наименования товара в корзине")
    public CartPage checkItemNameInTheCart(String itemName) {
        productName.shouldBe(Condition.visible).shouldHave(Condition.text(itemName));
        return this;
    }

}
