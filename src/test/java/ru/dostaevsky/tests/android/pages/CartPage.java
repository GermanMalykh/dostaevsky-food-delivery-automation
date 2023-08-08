package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import ru.dostaevsky.data.CartData;

import java.time.Duration;

public class CartPage {

    private final SelenideElement emptyCartInfo = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/textEmptyDescription"));
    private final SelenideElement emptyCartImage = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/imageEmpty"));
    private final SelenideElement minimalPriceErrorLayout = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/min_price_error_layout"));
    private final SelenideElement productTitle = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/textTitle"));
    private final SelenideElement productPrice = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/textProductPrice"));
    private final SelenideElement productCount = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/textProductCount"));

    @Step("Проверяем наличие сообщения с предложением добавления товара в корзину")
    public CartPage checkCartEmptyInfo() {
        emptyCartInfo.shouldHave(Condition.text(CartData.EMPTY_CART_INFO_TEXT));
        return this;
    }

    @Step("Проверяем наличие изображения пустой корзины")
    public void checkCartEmptyImage() {
        emptyCartImage.shouldBe(Condition.visible);
    }

    @Step("Проверяем наличие сообщения с текстом минимальной суммы заказа")
    public void checkMinimalPriceTitle(String minimalPrice) {
        minimalPriceErrorLayout
                .$(AppiumBy.id("ru.dostaevsky.android:id/min_price_error_title"))
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Минимальная сумма заказа — " + minimalPrice + " ₽."));
    }

    @Step("Проверяем отображение наименования товара \"{itemName}\" в корзине")
    public CartPage checkItemNameInTheCart(String itemName) {
        productTitle.shouldHave(Condition.text(itemName));
        return this;
    }

    @Step("Проверяем отображение цены товара в корзине")
    public CartPage checkItemPriceInTheCart(int parsedPrice, int count) {
        productPrice.shouldHave(Condition.text(String.valueOf(parsedPrice * count)));
        return this;
    }

    @Step("Проверяем отображение количества товара \"{itemCount}\" в корзине")
    public void checkItemCountInTheCart(String itemCount) {
        productCount.shouldHave(Condition.text(itemCount));
    }

}
