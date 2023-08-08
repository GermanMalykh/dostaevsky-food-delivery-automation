package ru.dostaevsky.tests.android.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.data.MenuItemsData;
import ru.dostaevsky.enums.Category;
import ru.dostaevsky.enums.CityName;
import ru.dostaevsky.enums.Onigiri;
import ru.dostaevsky.tests.android.config.PreRunConfig;
import ru.dostaevsky.tests.android.pages.FavoritePage;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.ItemComponents;
import ru.dostaevsky.tests.android.pages.components.NavigationComponents;

@Disabled(
        "Тест выключен из-за проблем со скроллом через эмулятор на BROWSERSTACK. "
                + "Включить для локального запуска"
)
@Tag("android")
@DisplayName("Android Tests")
public class FavoriteInfoTests extends PreRunConfig {
    MainPage main = new MainPage();
    NavigationComponents navigation = new NavigationComponents();
    ItemComponents item = new ItemComponents();
    FavoritePage favorite = new FavoritePage();

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("[Android] Добавление товара в избранное и проверка отображения товара в списке избранных")
    @Test
    void addItemToFavoriteListAndCheckIt() {
        main.selectByText(CityName.SPB.getDisplayName())
                .closingTechInfo();
        navigation.scrollToElement(Category.ONIGIRI.getValue())
                .scrollToElement(Onigiri.SNOW_CRAB_ONIGIRI.getValue());
        item.addItemToFavorite();
        navigation.backNavigation();
        main.selectByText(MenuItemsData.ADDITIONAL_INFO);
        main.selectByText(MenuItemsData.FAVORITE);
        favorite.checkFavoriteTitle();
        item.checkProductName(Onigiri.SNOW_CRAB_ONIGIRI.getValue());
    }
}
