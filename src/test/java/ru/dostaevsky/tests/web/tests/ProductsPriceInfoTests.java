package ru.dostaevsky.tests.web.tests;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.dostaevsky.tests.web.config.PreRunConfig;
import ru.dostaevsky.enums.CityLink;
import ru.dostaevsky.enums.CityName;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.CatalogItemComponents;

import java.util.*;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.enums.Category.*;

@Tag("web")
@DisplayName("Web Tests")
public class ProductsPriceInfoTests extends PreRunConfig {
    MainPage mainPage = new MainPage();
    CatalogItemComponents catalogItemComponents = new CatalogItemComponents();

    @Severity(CRITICAL)
    @DisplayName("[WEB] Отображение цен на завтрак. ")
    @ParameterizedTest(name = "В городе \"{0}\" отображаемые на странице цены на завтрак равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BreakfastsPriceProvider#provide")
    void breakfastPriceComparisonInSelectedCityTest(CityName name, CityLink link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateToCategory(BREAKFASTS)
                .removeInfoFromPage();
        catalogItemComponents.checkSizeComponentsOnPage(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

    @Severity(CRITICAL)
    @DisplayName("[WEB] Отображение цен на боулы за пределами СПБ. ")
    @ParameterizedTest(name = "В городе \"{0}\" отображаемые на странице цены на боулы равны ценам из словаря")
    @MethodSource("ru.dostaevsky.tests.web.providers.BowlsPriceProvider#provide")
    void bowlsPriceComparisonInSelectedCityTest(CityName name, CityLink link, Map<String, Integer> expectedPrices) {
        mainPage.openMainPage()
                .selectCityFromList(link.getValue(), name.getDisplayName())
                .navigateToCategory(BOWLS)
                .removeInfoFromPage();
        catalogItemComponents.checkSizeComponentsOnPage(expectedPrices)
                .assertPrices(expectedPrices, catalogItemComponents.getActualPrices());
    }

}
