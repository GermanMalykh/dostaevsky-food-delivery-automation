package ru.dostaevsky.tests.web;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.web.config.TestBaseWeb;
import ru.dostaevsky.tests.web.pages.BucketPage;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.CatalogItemComponents;
import ru.dostaevsky.tests.web.pages.components.HeaderComponents;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.tests.web.data.AttributeData.*;
import static ru.dostaevsky.tests.web.enums.Categories.*;

@DisplayName("Web Tests")
public class BucketInfoTests extends TestBaseWeb {
    MainPage mainPage = new MainPage();
    CatalogItemComponents catalogItemComponents = new CatalogItemComponents();
    BucketPage bucketPage = new BucketPage();
    HeaderComponents headerComponents = new HeaderComponents();

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара")
    @Test
    void addItemToCartAndCheckValueTest() {
        mainPage.openMainPage()
                .navigateToCategory(BOWLS);

        catalogItemComponents.addItemToCart();
        String price = catalogItemComponents.getAttributeValue(ATTRIBUTE_ITEM_PRICE);
        String name = catalogItemComponents.getAttributeValue(ATTRIBUTE_ITEM_NAME);

        headerComponents.checkItemPriceInHeaderCart(price)
                .checkItemCountInHeaderCart(ITEM_COUNT)
                .navigateToBucket();

        bucketPage.checkItemPriceInTheBucket(price)
                .checkItemNameInTheBucket(name)
                .checkItemCountInTheBucket(ITEM_COUNT);
    }

    @Severity(MINOR)
    @DisplayName("Отображение информации в пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        mainPage.openMainPage();
        headerComponents.navigateToBucket();
        bucketPage.checkBucketEmptyInfo()
                .checkBucketEmptyImage();
    }

    @Severity(NORMAL)
    @DisplayName("Отображение информации о минимальной сумме заказа")
    @Test
    void addItemToCartAndCheckMinimalPriceToDeliveryInfo() {
        mainPage.openMainPage()
                .navigateToCategory(WOKS);
        catalogItemComponents.addItemToCart();
        headerComponents.navigateToBucket();
        bucketPage.checkMinimalPriceTitle();
    }

}
