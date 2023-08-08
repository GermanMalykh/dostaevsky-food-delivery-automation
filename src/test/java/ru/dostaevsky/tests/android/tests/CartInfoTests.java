package ru.dostaevsky.tests.android.tests;

import io.qameta.allure.Severity;

import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.dostaevsky.data.MenuItemsData;
import ru.dostaevsky.enums.Category;
import ru.dostaevsky.enums.CityName;
import ru.dostaevsky.tests.android.config.PreRunConfig;
import ru.dostaevsky.tests.android.pages.CartPage;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.ItemComponents;

@Tag("android")
@DisplayName("Android Tests")
public class CartInfoTests extends PreRunConfig {
    CartPage cart = new CartPage();
    MainPage main = new MainPage();
    ItemComponents item = new ItemComponents();

    @Severity(SeverityLevel.MINOR)
    @DisplayName("[Android] Отображение информации в пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        main.selectByText(CityName.SPB.getDisplayName())
                .closingTechInfo();
        main.selectByText(MenuItemsData.CART);
        cart.checkCartEmptyInfo()
                .checkCartEmptyImage();
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("[Android] Проверка минимальной цены доставки. ")
    @ParameterizedTest(name = "Минимальная цена доставки в городе \"{1}\" равна \"{3}\" ₽")
    @CsvFileSource(resources = "/csv/cityWebInfo.csv")
    void checkMinimalPriceToDeliveryInfo(String link, String city, String phone, String minimalPrice) {
        main.selectByText(city).closingTechInfo();
        main.selectByText(Category.FAST_FOOD.getValue());
        item.addProductToCart();
        main.selectByText(MenuItemsData.CART);
        cart.checkMinimalPriceTitle(minimalPrice);
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("[Android] Добавление позиции в корзину и проверка отображения цены, количества и наименования товара в корзине")
    @Test
    void addItemToCartAndCheckValueInCartTest() {
        main.selectByText(CityName.SPB.getDisplayName())
                .closingTechInfo();
        main.selectByText(Category.NEW.getValue());
        item.addProductToCart()
                .addMoreProductsToCart();

        int price = item.getProductPrice();
        String name = item.getProductName();
        int count = item.getProductCount();

        main.selectByText(MenuItemsData.CART);
        cart.checkItemNameInTheCart(name)
                .checkItemPriceInTheCart(price, count)
                .checkItemCountInTheCart(String.valueOf(count));
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("[Android] Добавление позиции в корзину и проверка отображения цены, количества и наименования товара в уведомлении")
    @Test
    void addItemToCartAndCheckValueInNotificationTest() {
        main.selectByText(CityName.SPB.getDisplayName())
                .closingTechInfo();
        main.selectByText(Category.NEW.getValue());
        item.addProductToCart()
                .addMoreProductsToCart();

        int count = item.getProductCount();

        item.checkTotalItemsInCartNotification(String.valueOf(count));
    }
}
