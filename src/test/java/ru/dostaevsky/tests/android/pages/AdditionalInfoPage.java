package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;

public class AdditionalInfoPage {

    private final static SelenideElement
            cityName = $(id("ru.dostaevsky.android:id/cityTextView")),
            cityPhoneNumber = $(id("ru.dostaevsky.android:id/call_text"));

    @Step("Проверяем, что наименование выбранного города \"{city}\" отображается на экране")
    public AdditionalInfoPage checkCityName(String city) {
        cityName.shouldHave(text(city));
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается контактный номер телефона \"{phoneWithPlus}\"")
    public AdditionalInfoPage checkCityPhoneNumber(String phoneWithPlus) {
        cityPhoneNumber.shouldHave(text(phoneWithPlus));
        return this;
    }

}
