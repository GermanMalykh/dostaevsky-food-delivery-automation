package ru.dostaevsky.tests.android.pages;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.appium.AppiumSelectors.byText;

public class MainPage {

    @Step("Выбираем элемент с наименованием \"{value}\"")
    public MainPage selectByText(String value) {
        $(byText(value))
                .shouldBe(visible, Duration.ofSeconds(90))
                .click();
        return this;
    }

}
