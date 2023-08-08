package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.appium.SelenideAppium;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

public class AdditionalInfoPage {

    private final SelenideElement cityName = SelenideAppium.$(AppiumBy.id("ru.dostaevsky.android:id/cityTextView"));
    private final SelenideElement cityPhoneNumber = SelenideAppium.$(AppiumBy.id("ru.dostaevsky.android:id/call_text"));

    @Step("Проверяем, что наименование выбранного города \"{city}\" отображается на экране")
    public AdditionalInfoPage checkCityName(String city) {
        cityName.shouldHave(Condition.text(city));
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается контактный номер телефона \"{phoneWithPlus}\"")
    public void checkCityPhoneNumber(String phoneWithPlus) {
        cityPhoneNumber.shouldHave(Condition.text(phoneWithPlus));
    }

}
