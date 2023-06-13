package ru.dostaevsky.tests.web;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.dostaevsky.tests.web.enums.CityLinks;
import ru.dostaevsky.tests.web.enums.CityName;
import ru.dostaevsky.tests.web.pages.BucketPage;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.CatalogItemComponents;
import ru.dostaevsky.tests.web.pages.components.HeaderComponents;

import java.util.*;

import static io.qameta.allure.SeverityLevel.*;

@DisplayName("Web Tests")
public class DostaevskyTest extends TestBaseWeb {

    MainPage mainPage = new MainPage();
    CatalogItemComponents catalogItemComponents = new CatalogItemComponents();
    BucketPage bucketPage = new BucketPage();
    HeaderComponents headerComponents = new HeaderComponents();

    //    TODO: Вынести в класс с переменными
    String count = "1";
    private static final String
            attributeItemPrice = "data-price",
            attributeItemName = "data-name";

    @Severity(CRITICAL)
    @DisplayName("Выбор и отображение информации города. ")
    @ParameterizedTest(name = "Для города \"{1}\" отображается информация по ссылке \"{0}\" с контактным номером телефона \"{2}\"")
    @CsvFileSource(resources = "/csv/cityWebInfo.csv")
    void selectingCityFromTheListAvailableAndCheckingDisplayInfoTest(String link, String city, String phone) {
        mainPage.openMainPage()
                .selectCityFromList(link, city)
                .checkCityUrl(link)
                .checkCityNameOnPage(link)
                .checkCityPhoneOnPage(phone);
    }

    @Severity(CRITICAL)
    @DisplayName("Отображение цен на завтрак. ")
    @ParameterizedTest(name = "В городе {0} отображаемые на странице цены на завтрак равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BreakfastPriceProvider#provide")
    void breakfastPriceComparisonInSelectedCityTest(CityName name, CityLinks link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateTo("Завтраки");

        catalogItemComponents.checkSize(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

    @Severity(CRITICAL)
    @DisplayName("Отображение цен на боулы за пределами СПБ. ")
    @ParameterizedTest(name = "В городе \"{0}\" отображаемые на странице цены на боулы равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BowlsPriceProvider#provide")
    void bowlsPriceComparisonInSelectedCityTest(CityName name, CityLinks link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateTo("Боулы");

        catalogItemComponents.checkSize(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара")
    @Test
    void addItemToCartAndCheckValueTest() {
        mainPage.openMainPage()
                .navigateTo("Боулы");

        catalogItemComponents.addItemToCart();
        String price = catalogItemComponents.gettingDataFromAttribute(attributeItemPrice);
        String name = catalogItemComponents.gettingDataFromAttribute(attributeItemName);

        headerComponents.checkingTheTotalAmountOfTheItemInTheHeaderCart(price)
                .checkingTheTotalNumberOfItemsInTheHeaderCart(count)
                .navigateToBucket();

        bucketPage.checkingThePriceOfTheItemAddedToTheCart(price)
                .checkingTheNameOfTheProductAddedToTheCart(name)
                .checkingTheQuantityOfTheItemAddedToTheCart(count);
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
                .navigateTo("Wok");
        catalogItemComponents.addItemToCart();
        headerComponents.navigateToBucket();
        bucketPage.checkBucketLimitTitle();
    }

}
