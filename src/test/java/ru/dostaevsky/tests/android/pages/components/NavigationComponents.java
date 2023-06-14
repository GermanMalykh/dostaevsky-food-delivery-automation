package ru.dostaevsky.tests.android.pages.components;

import io.qameta.allure.Step;

import static com.codeborne.selenide.appium.AppiumSelectors.byText;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.appium.SelenideAppium.back;

public class NavigationComponents {
    @Step("Вызываем навигацию к предыдущему экрану")
    public NavigationComponents backNavigation() {
        back();
        return this;
    }

    @Step("Скроллим экран до \"{value}\" и переходим к деталям")
    public NavigationComponents scrollToElement(String value) {
        $(byText((value))).scrollTo().click();
        return this;
    }

}
