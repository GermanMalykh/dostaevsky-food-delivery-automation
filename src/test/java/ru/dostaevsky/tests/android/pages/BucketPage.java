package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;
import static ru.dostaevsky.data.BucketData.*;

public class BucketPage {

    private final static SelenideElement
            emptyBucketInfo = $(id("ru.dostaevsky.android:id/textEmptyDescription")),
            emptyBucketImage = $(id("ru.dostaevsky.android:id/imageEmpty")),
            minimalPriceErrorLayout = $(id("ru.dostaevsky.android:id/min_price_error_layout")),
            minimalPriceErrorTitle = $(id("ru.dostaevsky.android:id/min_price_error_title")),
            productTitle = $(id("ru.dostaevsky.android:id/textTitle")),
            productPrice = $(id("ru.dostaevsky.android:id/textProductPrice")),
            productCount = $(id("ru.dostaevsky.android:id/textProductCount"));

    @Step("Проверяем наличие сообщения с предложением добавления позиций в корзину")
    public BucketPage checkBucketEmptyInfo() {
        emptyBucketInfo.shouldHave(text(EMPTY_BUCKET_INFO_TEXT));
        return this;
    }

    @Step("Проверяем наличие изображения пустой корзины")
    public BucketPage checkBucketEmptyImage() {
        emptyBucketImage.shouldBe(visible);
        return this;
    }

    //    TODO: Перевести в параметризованный тест с проверкой суммы по городам
    @Step("Проверяем наличие сообщения с текстом минимальной суммы заказа")
    public BucketPage checkMinimalPriceTitle() {
        minimalPriceErrorLayout
                .$(id("ru.dostaevsky.android:id/min_price_error_title"))
                .shouldHave(text(MINIMAL_PRICE_TEXT_MOBILE));
        return this;
    }

    @Step("Проверяем отображение наименования товара \"{itemName}\" в корзине")
    public BucketPage checkItemNameInTheBucket(String itemName) {
        productTitle.shouldHave(text(itemName));
        return this;
    }

    @Step("Проверяем отображение цены товара в корзине")
    public BucketPage checkItemPriceInTheBucket(int parsedPrice, int count) {
        productPrice.shouldHave(text(String.valueOf(parsedPrice * count)));
        return this;
    }

    @Step("Проверяем отображение количества товара \"{itemCount}\" в корзине")
    public BucketPage checkItemCountInTheBucket(String itemCount) {
        productCount.shouldHave(text(itemCount));
        return this;
    }

}
