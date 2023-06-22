package ru.dostaevsky.tests.android.tests;

import io.qameta.allure.Severity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.dostaevsky.tests.android.config.PreRunConfig;
import ru.dostaevsky.tests.android.pages.CartPage;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.ItemComponents;
import ru.dostaevsky.tests.android.pages.components.NavigationComponents;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.data.MenuItemsData.CART;
import static ru.dostaevsky.enums.Categories.*;
import static ru.dostaevsky.enums.CityName.SPB;

@Tag("android")
@DisplayName("Android Tests")
public class CartInfoTests extends PreRunConfig {
    CartPage cart = new CartPage();
    MainPage main = new MainPage();
    NavigationComponents navigation = new NavigationComponents();
    ItemComponents item = new ItemComponents();

    @Severity(MINOR)
    @DisplayName("Отображение информации в пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        main.selectByText(SPB.getDisplayName());
        navigation.backNavigation();
        main.selectByText(CART);
        cart.checkCartEmptyInfo()
                .checkCartEmptyImage();
    }

    @Severity(NORMAL)
    @DisplayName("Проверка минимальной цены доставки. ")
    @ParameterizedTest(name = "Минимальная цена доставки в городе \"{1}\" равна \"{3}\" ₽")
    @CsvFileSource(resources = "/csv/cityWebInfo.csv")
    void checkMinimalPriceToDeliveryInfo(String link, String city, String phone, String minimalPrice) {
        main.selectByText(city);
        navigation.backNavigation();
        main.selectByText(FAST_FOOD.getValue());
        item.addProductToCart();
        main.selectByText(CART);
        cart.checkMinimalPriceTitle(minimalPrice);
    }

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара в корзине")
    @Test
    void addItemToCartAndCheckValueInCartTest() {
        main.selectByText(SPB.getDisplayName());
        navigation.backNavigation();
        main.selectByText(NEW.getValue());
        item.addProductToCart()
                .addMoreProductsToCart();

        int price = item.getProductPrice();
        String name = item.getProductName();
        int count = item.getProductCount();

        main.selectByText(CART);
        cart.checkItemNameInTheCart(name)
                .checkItemPriceInTheCart(price, count)
                .checkItemCountInTheCart(String.valueOf(count));
    }

    @Severity(NORMAL)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара в уведомлении")
    @Test
    void addItemToCartAndCheckValueInNotificationTest() {
        main.selectByText(SPB.getDisplayName());
        navigation.backNavigation();
        main.selectByText(NEW.getValue());
        item.addProductToCart()
                .addMoreProductsToCart();

        int count = item.getProductCount();

        item.checkTotalItemsInCartNotification(String.valueOf(count));
    }
}
