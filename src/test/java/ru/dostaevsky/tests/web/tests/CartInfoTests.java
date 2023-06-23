package ru.dostaevsky.tests.web.tests;

import io.qameta.allure.Severity;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.dostaevsky.enums.CityLinks;
import ru.dostaevsky.tests.api.client.DostaevskyApiClient;
import ru.dostaevsky.tests.api.helpers.ResponseValueExtractor;
import ru.dostaevsky.tests.api.models.BasketInfoResponse;
import ru.dostaevsky.tests.web.config.PreRunConfig;
import ru.dostaevsky.tests.web.pages.CartPage;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.CatalogItemComponents;
import ru.dostaevsky.tests.web.pages.components.HeaderComponents;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.data.AttributeData.*;
import static ru.dostaevsky.data.CartData.SPB_CART_URL;
import static ru.dostaevsky.data.AuthData.API_SPB_UNREGISTERED_USER_COOKIE;
import static ru.dostaevsky.enums.BurgerIds.DOR_BLUE_BURGER_ID;
import static ru.dostaevsky.enums.Categories.*;

@Tag("web")
@DisplayName("Web Tests")
public class CartInfoTests extends PreRunConfig {
    MainPage main = new MainPage();
    CatalogItemComponents item = new CatalogItemComponents();
    CartPage cart = new CartPage();
    HeaderComponents header = new HeaderComponents();
    protected ValidatableResponse response;
    DostaevskyApiClient apiClient = new DostaevskyApiClient();
    ResponseValueExtractor responseExtractor = new ResponseValueExtractor();

    @Severity(NORMAL)
    @DisplayName("[WEB] Проверка отображения цены и количества товара в шапке страницы")
    @Test
    void addItemToCartAndCheckValueInHeader() {
        main.openMainPage()
                .navigateToCategory(BOWLS);
        item.addItemToCart();

        String price = item.getAttributeValue(ATTRIBUTE_ITEM_PRICE),
                count = item.getItemCount();

        header.checkItemPriceInHeaderCart(price)
                .checkItemCountInHeaderCart(count);
    }

    @Severity(BLOCKER)
    @DisplayName("[WEB] Проверка отображения цены, количества и наименования товара в корзине")
    @Test
    void checkAddedItemValueInCart() {
        RestAssured.baseURI = CityLinks.SPB_LINK.getValue();
        step("Делаем запрос на добавление товара в корзину", () -> {
            response = apiClient.addingItemToBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue()).statusCode(200);
        });
        step("Делаем запрос на получение информации о товарах в корзине", () -> {
            response = apiClient.gettingBasketInfo(API_SPB_UNREGISTERED_USER_COOKIE).statusCode(200);
        });

        BasketInfoResponse basketInfoResponse = response.extract().as(BasketInfoResponse.class);
        String price = String.valueOf(basketInfoResponse.getPrice());
        String count = String.valueOf(basketInfoResponse.getItems()[0].getQuantity());
        String name = basketInfoResponse.getItems()[0].getTitle();

        main.addingUserCookie()
                .openDesiredPage(SPB_CART_URL);
        cart.checkItemPriceInTheCart(price)
                .checkItemNameInTheCart(name)
                .checkItemCountInTheCart(count);

        step("Делаем запрос на удаление товара из корзины", () -> {
            String data = response.extract().asString();
            String itemUid = responseExtractor.getItemUidAfterGettingBasketInfo(data);
            response = apiClient.removeItemFromBasket(API_SPB_UNREGISTERED_USER_COOKIE,
                    DOR_BLUE_BURGER_ID.getValue(), itemUid).statusCode(200);
        });
    }

    @Severity(MINOR)
    @DisplayName("[WEB] Проверка отображения информации о пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        main.openMainPage();
        header.navigateToCart();
        cart.checkCartEmptyInfo()
                .checkCartEmptyImage();
    }

    @Severity(NORMAL)
    @DisplayName("[WEB] Проверка минимальной цены доставки. ")
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
