package ru.dostaevsky.tests.web;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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
    @Severity(NORMAL)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены и количества товара в шапке страницы")
    @Test
    void addItemToCartAndCheckValueTestInHeader() {
        main.openMainPage()
                .navigateToCategory(BOWLS);
        item.addItemToCart();

        String price = item.getAttributeValue(ATTRIBUTE_ITEM_PRICE),
                count = item.getItemCount();

        header.checkItemPriceInHeaderCart(price)
                .checkItemCountInHeaderCart(count);
    }

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара в корзине")
    @Test
    void addItemToCartAndCheckValueTestInCart() {
        main.openMainPage()
                .navigateToCategory(BOWLS);
        item.addItemToCart();

        String price = item.getAttributeValue(ATTRIBUTE_ITEM_PRICE),
                name = item.getAttributeValue(ATTRIBUTE_ITEM_NAME),
                count = item.getItemCount();

        header.navigateToCart();
        cart.checkItemPriceInTheCart(price)
                .checkItemNameInTheCart(name)
                .checkItemCountInTheCart(count);
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
    @DisplayName("Проверка минимальной цены доставки. ")
    @ParameterizedTest(name = "Минимальная цена доставки в городе \"{1}\" равна \"{3}\" ₽")
    @CsvFileSource(resources = "/csv/cityWebInfo.csv")
    void addItemToCartAndCheckMinimalPriceToDeliveryInfo(String link, String city, String phone, String minimalPrice) {
        main.openMainPage()
                .hideConfirmCityMessage()
                .selectCityFromList(link, city)
                .navigateToCategory(WOKS);
        item.addItemToCart();
        header.navigateToCart();
        cart.checkMinimalPriceTitle(minimalPrice);
    }

}
