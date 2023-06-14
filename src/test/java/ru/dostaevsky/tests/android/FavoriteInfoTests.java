package ru.dostaevsky.tests.android;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.android.config.TestBaseMobile;
import ru.dostaevsky.tests.android.pages.FavoritePage;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.ItemComponents;
import ru.dostaevsky.tests.android.pages.components.NavigationComponents;

import static io.qameta.allure.SeverityLevel.NORMAL;
import static ru.dostaevsky.enums.CityName.SPB;

@Tag("android")
@DisplayName("Android Tests")
public class FavoriteInfoTests extends TestBaseMobile {
    MainPage main = new MainPage();
    NavigationComponents navigation = new NavigationComponents();
    ItemComponents item = new ItemComponents();
    FavoritePage favorite = new FavoritePage();

    @Severity(NORMAL)
    @DisplayName("Добавление товара в избранное и проверка отображения товара в списке избранных")
    @Test
    void addItemToFavoriteListAndCheckIt() {
        main.selectByText(SPB.getDisplayName());
        navigation.backNavigation()
                .scrollToElement("Онигири")
                .scrollToElement("Онигири со снежным крабом");
        item.addItemToFavorite();
        navigation.backNavigation();
        main.selectByText("Еще");
        main.selectByText("Избранное");
        favorite.checkFavoriteTitle();
        item.checkProductName("Онигири со снежным крабом");
    }
}
