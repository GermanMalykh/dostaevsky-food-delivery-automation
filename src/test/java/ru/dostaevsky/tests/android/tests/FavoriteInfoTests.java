package ru.dostaevsky.tests.android.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.android.config.PreRunConfig;
import ru.dostaevsky.tests.android.pages.FavoritePage;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.ItemComponents;
import ru.dostaevsky.tests.android.pages.components.NavigationComponents;

import static io.qameta.allure.SeverityLevel.NORMAL;
import static ru.dostaevsky.data.MenuItemsData.ADDITIONAL_INFO;
import static ru.dostaevsky.data.MenuItemsData.FAVORITE;
import static ru.dostaevsky.enums.Category.DESSERTS;
import static ru.dostaevsky.enums.CityName.SPB;
import static ru.dostaevsky.enums.Desserts.CARAMEL_CHEESECAKE;

@Tag("android")
@DisplayName("Android Tests")
public class FavoriteInfoTests extends PreRunConfig {
    MainPage main = new MainPage();
    NavigationComponents navigation = new NavigationComponents();
    ItemComponents item = new ItemComponents();
    FavoritePage favorite = new FavoritePage();

    @Tag("only-local")
    @Severity(NORMAL)
    @Description(
            "Тест выключен из-за проблем со скроллом через эмулятор на BROWSERSTACK. " +
                    "Только для локального запуска"
    )
    @DisplayName("[Android] Добавление товара в избранное и проверка отображения товара в списке избранных")
    @Test
    void addItemToFavoriteListAndCheckIt() {
        main.selectByText(SPB.getDisplayName());
        navigation.scrollToElement(DESSERTS.getValue())
                .scrollToElement(CARAMEL_CHEESECAKE.getValue());
        item.addItemToFavorite();
        navigation.backNavigation();
        main.selectByText(ADDITIONAL_INFO);
        main.selectByText(FAVORITE);
        favorite.checkFavoriteTitle();
        item.checkProductName(CARAMEL_CHEESECAKE.getValue());
    }
}
