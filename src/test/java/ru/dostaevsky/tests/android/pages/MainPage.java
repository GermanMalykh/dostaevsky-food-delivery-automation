package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.AppiumSelectors.byText;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;

public class MainPage {

    private final static SelenideElement
            techInfoButton = $(id("ru.dostaevsky.android:id/tech_apply_btn"));

    @Step("Выбираем элемент с наименованием \"{value}\"")
    public MainPage selectByText(String value) {
        $(byText(value))
                .shouldBe(visible, Duration.ofSeconds(90))
                .click();
        return this;
    }

    @Step("Закрываем окно дополнительной информации")
    public MainPage closingTechInfo() {
        techInfoButton.shouldBe(visible, Duration.ofSeconds(90))
                .click();
        return this;
    }


}
