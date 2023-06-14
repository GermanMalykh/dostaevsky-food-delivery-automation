package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.className;

public class FavoritePage {

    private final static SelenideElement
            favoriteTitle = $(className(("android.widget.TextView")));

    @Step("Проверяем наименование вкладки \"Избранное\"")
    public FavoritePage checkFavoriteTitle() {
        favoriteTitle
                .shouldHave(text("Избранное"));
        return this;
    }
}
