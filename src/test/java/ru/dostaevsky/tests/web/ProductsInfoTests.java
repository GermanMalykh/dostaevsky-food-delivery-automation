package ru.dostaevsky.tests.web;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.dostaevsky.tests.web.config.TestBaseWeb;
import ru.dostaevsky.tests.web.enums.CityLinks;
import ru.dostaevsky.tests.web.enums.CityName;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.CatalogItemComponents;

import java.util.*;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.tests.web.enums.Categories.*;

@DisplayName("Web Tests")
public class ProductsInfoTests extends TestBaseWeb {
    MainPage mainPage = new MainPage();
    CatalogItemComponents catalogItemComponents = new CatalogItemComponents();

    @Severity(CRITICAL)
    @DisplayName("Отображение цен на завтрак. ")
    @ParameterizedTest(name = "В городе \"{0}\" отображаемые на странице цены на завтрак равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BreakfastsPriceProvider#provide")
    void breakfastPriceComparisonInSelectedCityTest(CityName name, CityLinks link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .hideConfirmCityMessage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateToCategory(BREAKFASTS)
                .removeInfoFromPage();

        catalogItemComponents.checkSizeComponentsOnPage(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

    @Severity(CRITICAL)
    @DisplayName("Отображение цен на боулы за пределами СПБ. ")
    @ParameterizedTest(name = "В городе \"{0}\" отображаемые на странице цены на боулы равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BowlsPriceProvider#provide")
    void bowlsPriceComparisonInSelectedCityTest(CityName name, CityLinks link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .hideConfirmCityMessage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateToCategory(BOWLS)
                .removeInfoFromPage();

        catalogItemComponents.checkSizeComponentsOnPage(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

}
