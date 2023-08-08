package ru.dostaevsky.tests.web.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.dostaevsky.enums.Category;
import ru.dostaevsky.tests.web.config.PreRunConfig;
import ru.dostaevsky.enums.CityLink;
import ru.dostaevsky.enums.CityName;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.CatalogItemComponents;

import java.util.Map;

@Tag("web")
@DisplayName("Web Tests")
public class ProductsPriceInfoTests extends PreRunConfig {
    MainPage mainPage = new MainPage();
    CatalogItemComponents catalogItemComponents = new CatalogItemComponents();

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("[WEB] Отображение цен на завтрак. ")
    @ParameterizedTest(name = "В городе \"{0}\" отображаемые на странице цены на завтрак равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BreakfastsPriceProvider#provide")
    void breakfastPriceComparisonInSelectedCityTest(CityName name, CityLink link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .hideConfirmCityMessage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateToCategory(Category.BREAKFASTS)
                .removeInfoFromPage();
        catalogItemComponents.checkSizeComponentsOnPage(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("[WEB] Отображение цен на боулы за пределами СПБ. ")
    @ParameterizedTest(name = "В городе \"{0}\" отображаемые на странице цены на боулы равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BowlsPriceProvider#provide")
    void bowlsPriceComparisonInSelectedCityTest(CityName name, CityLink link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .hideConfirmCityMessage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateToCategory(Category.BOWLS)
                .removeInfoFromPage();
        catalogItemComponents.checkSizeComponentsOnPage(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

}
