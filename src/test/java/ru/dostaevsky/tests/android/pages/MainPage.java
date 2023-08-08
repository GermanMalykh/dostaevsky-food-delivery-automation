package ru.dostaevsky.tests.android.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.appium.AppiumSelectors;
import com.codeborne.selenide.appium.SelenideAppium;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import java.time.Duration;

public class MainPage {

    private final SelenideElement techInfoButton = SelenideAppium.$(AppiumBy.id("ru.dostaevsky.android:id/tech_apply_btn"));

    @Step("Выбираем элемент с наименованием \"{value}\"")
    public MainPage selectByText(String value) {
        SelenideAppium.$(AppiumSelectors.byText(value))
                .shouldBe(Condition.visible, Duration.ofSeconds(90)).click();
        return this;
    }

    @Step("Закрываем окно дополнительной информации")
    public void closingTechInfo() {
        techInfoButton.shouldBe(Condition.visible, Duration.ofSeconds(90)).click();
    }
}
