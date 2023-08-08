package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.appium.SelenideAppium;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import ru.dostaevsky.data.MenuItemsData;

public class FavoritePage {

    private final SelenideElement favoriteTitle = SelenideAppium.$(AppiumBy.className(("android.widget.TextView")));

    @Step("Проверяем наименование вкладки \"Избранное\"")
    public void checkFavoriteTitle() {
        favoriteTitle.shouldHave(Condition.text(MenuItemsData.FAVORITE));
    }
}
