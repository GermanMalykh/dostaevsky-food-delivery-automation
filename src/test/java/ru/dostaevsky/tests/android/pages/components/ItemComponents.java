package ru.dostaevsky.tests.android.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

public class ItemComponents {
    private final SelenideElement addProductButton = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/addButton"));
    private final SelenideElement addMoreProductButton = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/buttonAddMoreProduct"));
    private final SelenideElement productPrice = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/textCardProductPrice"));
    private final SelenideElement productName = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/textProductName"));
    private final SelenideElement productCount = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/textViewCount"));
    private final SelenideElement bottomCart = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/bottomNavigationCartMenuId"));
    private final SelenideElement addToFavorite = Selenide.$(AppiumBy.id(("ru.dostaevsky.android:id/imageFavorite")));

    @Step("Добавляем позицию в корзину")
    public ItemComponents addProductToCart() {
        addProductButton.click();
        return this;
    }

    @Step("Добавляем дополнительную позицию в корзину")
    public void addMoreProductsToCart() {
        addMoreProductButton.click();
    }

    @Step("Извлекаем стоимость товара")
    public int getProductPrice() {
        return Integer.parseInt(productPrice.getText().replace(" ₽", ""));
    }

    @Step("Извлекаем наименование товара")
    public String getProductName() {
        return productName.getText();
    }

    @Step("Проверяем наименование товара \"{value}\"")
    public void checkProductName(String value) {
        productName.shouldHave(Condition.text(value));
    }

    @Step("Извлекаем количество добавленного товара")
    public int getProductCount() {
        return Integer.parseInt(productCount.getText());
    }

    @Step("Проверяем количество добавленных в корзину товаров в уведомлении")
    public void checkTotalItemsInCartNotification(String itemCount) {
        bottomCart.$(AppiumBy.accessibilityId("Корзина, " + itemCount + " new notifications"))
                .shouldBe(Condition.visible);
    }

    @Step("Добавляем позицию в избранное")
    public void addItemToFavorite() {
        addToFavorite.click();
    }

}
