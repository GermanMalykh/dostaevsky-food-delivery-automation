package ru.dostaevsky.tests.android.pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;

public class ItemComponents {
    private final static SelenideElement
            addProductButton = $(id("ru.dostaevsky.android:id/addButton")),
            addMoreProductButton = $(id("ru.dostaevsky.android:id/buttonAddMoreProduct")),
            productPrice = $(id("ru.dostaevsky.android:id/textCardProductPrice")),
            productName = $(id("ru.dostaevsky.android:id/textProductName")),
            productCount = $(id("ru.dostaevsky.android:id/textViewCount")),
            bottomCart = $(id("ru.dostaevsky.android:id/bottomNavigationCartMenuId")),
            addToFavorite = $(id(("ru.dostaevsky.android:id/imageFavorite")));

    @Step("Добавляем позицию в корзину")
    public ItemComponents addProductToCart() {
        addProductButton.click();
        return this;
    }

    @Step("Добавляем дополнительную позицию в корзину")
    public ItemComponents addMoreProductsToCart() {
        addMoreProductButton.click();
        return this;
    }

    @Step("Извлекаем стоимость товара")
    public int getProductPrice() {
        return Integer.parseInt(
                productPrice
                        .getText().replace(" ₽", ""));
    }

    @Step("Извлекаем наименование товара")
    public String getProductName() {
        return productName.getText();
    }

    @Step("Проверяем наименование товара \"{value}\"")
    public ItemComponents checkProductName(String value) {
        productName.shouldHave(text(value));
        return this;
    }

    @Step("Извлекаем количество добавленного товара")
    public int getProductCount() {
        return Integer.parseInt(productCount.getText());
    }

    @Step("Проверяем количество добавленных в корзину товаров в уведомлении")
    public ItemComponents checkTotalItemsInCartNotification(String itemCount) {
        bottomCart
                .$(accessibilityId("Корзина, " + itemCount + " new notifications"))
                .shouldBe(visible);
        return this;
    }

    @Step("Добавляем позицию в избранное")
    public ItemComponents addItemToFavorite() {
        addToFavorite.click();
        return this;
    }

}
