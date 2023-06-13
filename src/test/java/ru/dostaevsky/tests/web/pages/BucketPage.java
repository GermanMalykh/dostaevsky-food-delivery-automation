package ru.dostaevsky.tests.web.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.dostaevsky.tests.web.data.AttributeData.*;
import static ru.dostaevsky.tests.web.data.BucketData.*;

public class BucketPage {
    private final static SelenideElement
            emptyBucketInfo = $(".basket-empty-blank"),
            emptyBucketImage = $(".basket-empty-blank__img"),
            minimalPriceToDelivery = $(".basket-blank-limit__title"),
            productPrice = $(".basket__product-price__value"),
            productCount = $(".counter-buttons__count"),
            productName = $(".basket__product-title");

    @Step("Проверяем наличие сообщения с предложением добавления позиций в корзину")
    public BucketPage checkBucketEmptyInfo() {
        emptyBucketInfo
                .shouldHave(text(EMPTY_BUCKET_INFO_TEXT));
        return this;
    }

    @Step("Проверяем наличие изображения пустой корзины")
    public BucketPage checkBucketEmptyImage() {
        emptyBucketImage
                .shouldHave(attributeMatching(ATTRIBUTE_SRC, EMPTY_BUCKET_IMAGE_PATH));
        return this;
    }

    //TODO: Добавить параметризацию на тест с проверкой по городам
    @Step("Проверяем наличие сообщения с текстом минимальной суммы заказа")
    public BucketPage checkMinimalPriceTitle() {
        minimalPriceToDelivery
                .shouldBe(visible)
                .shouldHave(text(MINIMAL_PRICE_TEXT));
        return this;
    }

    @Step("Проверяем отображение цены товара в корзине")
    public BucketPage checkItemPriceInTheBucket(String itemPrice) {
        productPrice
                .shouldBe(visible)
                .shouldHave(text(itemPrice));
        return this;
    }

    //TODO: Доработать поиск элемента
    @Step("Проверяем отображение количества товара в корзине")
    public BucketPage checkItemCountInTheBucket(String itemCount) {
        productCount
                .shouldBe(visible)
                .shouldHave(text(itemCount));
        return this;
    }

    @Step("Проверяем отображение наименования товара в корзине")
    public BucketPage checkItemNameInTheBucket(String itemName) {
        productName
                .shouldBe(visible)
                .shouldHave(text(itemName));
        return this;
    }

}
