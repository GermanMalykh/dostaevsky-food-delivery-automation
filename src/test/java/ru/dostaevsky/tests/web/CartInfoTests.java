package ru.dostaevsky.tests.web;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.web.config.TestBaseWeb;
import ru.dostaevsky.tests.web.pages.CartPage;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.CatalogItemComponents;
import ru.dostaevsky.tests.web.pages.components.HeaderComponents;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.data.AttributeData.*;
import static ru.dostaevsky.enums.Categories.*;

@Tag("web")
@DisplayName("Web Tests")
public class CartInfoTests extends TestBaseWeb {
    MainPage main = new MainPage();
    CatalogItemComponents item = new CatalogItemComponents();
    CartPage cart = new CartPage();
    HeaderComponents header = new HeaderComponents();

    // TODO: Добавить в дальнейшем добавление товара через API
    // TODO: Вытаскивать значение count со страницы и убрать хардкод
    @Severity(NORMAL)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены и количества товара в шапке страницы")
    @Test
    void addItemToCartAndCheckValueTestInHeader() {
        main.openMainPage()
                .navigateToCategory(BOWLS);

        item.addItemToCart();
        String price = item.getAttributeValue(ATTRIBUTE_ITEM_PRICE);

        header.checkItemPriceInHeaderCart(price)
                .checkItemCountInHeaderCart(ITEM_COUNT)
                .navigateToCart();
    }

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара в корзине")
    @Test
    void addItemToCartAndCheckValueTestInCart() {
        main.openMainPage()
                .navigateToCategory(BOWLS);

        item.addItemToCart();
        String price = item.getAttributeValue(ATTRIBUTE_ITEM_PRICE);
        String name = item.getAttributeValue(ATTRIBUTE_ITEM_NAME);

        cart.checkItemPriceInTheCart(price)
                .checkItemNameInTheCart(name)
                .checkItemCountInTheCart(ITEM_COUNT);
    }

    @Severity(MINOR)
    @DisplayName("Отображение информации в пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        main.openMainPage();
        header.navigateToCart();
        cart.checkCartEmptyInfo()
                .checkCartEmptyImage();
    }

    @Severity(NORMAL)
    @DisplayName("Отображение информации о минимальной сумме заказа")
    @Test
    void addItemToCartAndCheckMinimalPriceToDeliveryInfo() {
        main.openMainPage()
                .navigateToCategory(WOKS);
        item.addItemToCart();
        header.navigateToCart();
        cart.checkMinimalPriceTitle();
    }

}
