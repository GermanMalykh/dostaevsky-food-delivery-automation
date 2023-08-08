package ru.dostaevsky.tests.android.pages.components;

import com.codeborne.selenide.appium.AppiumSelectors;
import com.codeborne.selenide.appium.SelenideAppium;
import io.qameta.allure.Step;

public class NavigationComponents {
    @Step("Вызываем навигацию к предыдущему экрану")
    public void backNavigation() {
        SelenideAppium.back();
    }

    @Step("Скроллим экран до \"{value}\" и переходим к деталям")
    public NavigationComponents scrollToElement(String value) {
        SelenideAppium.$(AppiumSelectors.byText((value))).scrollTo().click();
        return this;
    }

    //    @Step("Скроллим по координатам до \"{value}\" и переходим к деталям")
    //    public NavigationComponents scrollToElementByCoordinates(String scrollFrom, String value) {
    //        Selenide.actions().moveToElement(SelenideAppium.$(AppiumSelectors.byText((scrollFrom))))
    //                .clickAndHold().moveByOffset(-800, -1000).release().perform();
    //        SelenideAppium.$(AppiumSelectors.byText((value))).click();
    //        return this;
    //    }

}
